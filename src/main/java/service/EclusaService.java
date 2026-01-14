package service;

import dao.CapitaoDAO;
import dao.EmbarcacaoDAO;
import model.Capitao;
import model.Eclusa;
import model.Embarcacao;
import factory.EmbarcacaoFactory;
import java.util.List;

public class EclusaService {
    
    private Eclusa model;
    private CapitaoDAO capitaoDAO;
    private EmbarcacaoDAO embarcacaoDAO;

    public EclusaService(Eclusa model) {
        this.model = model;
        this.capitaoDAO = new CapitaoDAO();
        this.embarcacaoDAO = new EmbarcacaoDAO();
        
        // Carrega o banco e restaura o caixa
        this.carregarFilaDoBanco();
    }
    
    private void carregarFilaDoBanco() {
        try {
            List<Embarcacao> salvas = embarcacaoDAO.listarTodas(model.getTamanho(), capitaoDAO);
            
            if (model.getFila() != null && salvas != null) {
                model.getFila().addAll(salvas);
                
                // --- CORREÇÃO: Recalcula o caixa inicial ---
                // Percorre o que veio do banco e soma ao caixa (apenas uma vez)
                for (Embarcacao e : salvas) {
                    model.registrarPagamento(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Aviso: Não foi possível carregar a fila do banco: " + e.getMessage());
        }
    }

    // ========================================================================
    // GESTÃO DE EMBARCAÇÕES (Entrada, Saída e Pagamento)
    // ========================================================================
    
    public void adicionarEmbarcacao(String tipo, float comp, float larg, String ori, String dest, 
                                    String pais, int cI, Capitao cap, String sentido, Object... extras) throws Exception {
        
        this.validarCapitaoDisponivel(cap);

        Embarcacao nova = EmbarcacaoFactory.criar(tipo, model.getTamanho(), comp, larg, ori, dest, pais, cI, cap, sentido, extras);

        if (!model.setEmbarcacoes(cI, nova)) {
            throw new Exception("Já existe uma embarcação com ID " + cI + " na fila.");
        }
        
        // --- CORREÇÃO: Cobra o pedágio no momento do cadastro ---
        model.registrarPagamento(nova);
        
        // Salva no Banco
        embarcacaoDAO.salvar(nova);
    }
    
    public void removerEmbarcacaoDoBanco(int id) {
        try { 
            embarcacaoDAO.excluir(id); 
        } catch (Exception e) { 
            System.out.println("Erro ao excluir do banco: " + e.getMessage()); 
        }
    }

    private void validarCapitaoDisponivel(Capitao capitao) throws Exception {
        if (capitao == null) return; 
        int licenca = capitao.getNumLicenca();
        
        // Verifica na Fila
        for (Embarcacao e : model.getFila()) {
            if (e.getCapitao() != null && e.getCapitao().getNumLicenca() == licenca) 
                throw new Exception("Capitão " + capitao.getNome() + " já está na fila!");
        }
        
        // Verifica na Eclusa (COM PROTEÇÃO CONTRA NULL)
        Embarcacao naEclusa = model.getEmbarcacaoNaEclusa();
        if (naEclusa != null && naEclusa.getCapitao() != null) {
            if (naEclusa.getCapitao().getNumLicenca() == licenca) {
                throw new Exception("Capitão " + capitao.getNome() + " está dentro da eclusa!");
            }
        }
    }
    
    // ========================================================================
    // REGRAS DE NÍVEL DA ÁGUA (Subir/Descer)
    // ========================================================================

    public void validarEntrada(Embarcacao proxima, String statusEclusa) throws Exception {
        if (proxima == null) throw new Exception("Fila vazia.");
        
        String sentido = proxima.getSentido(); 

        if (sentido.equalsIgnoreCase("Subir") && !statusEclusa.equalsIgnoreCase("seca")) {
            throw new Exception("Para SUBIR (Mar->Rio), a eclusa precisa estar SECA.");
        }
        
        if (sentido.equalsIgnoreCase("Descer") && !statusEclusa.equalsIgnoreCase("cheia")) {
            throw new Exception("Para DESCER (Rio->Mar), a eclusa precisa estar CHEIA.");
        }
    }

    public void validarSaida(Embarcacao naEclusa, String statusEclusa) throws Exception {
        if (naEclusa == null) throw new Exception("Eclusa vazia.");

        String sentido = naEclusa.getSentido();

        if (sentido.equalsIgnoreCase("Subir") && !statusEclusa.equalsIgnoreCase("cheia")) {
            throw new Exception("A embarcação está subindo! Encha a eclusa para sair.");
        }

        if (sentido.equalsIgnoreCase("Descer") && !statusEclusa.equalsIgnoreCase("seca")) {
            throw new Exception("A embarcação está descendo! Esvazie a eclusa para sair.");
        }
    }

    // ========================================================================
    // GESTÃO DE CAPITÃES
    // ========================================================================

    public void cadastrarCapitao(String nome, String licencaTexto, String contato) throws Exception {
        if (nome == null || nome.trim().isEmpty() || licencaTexto == null || contato == null) {
            throw new Exception("Todos os dados do capitão são obrigatórios.");
        }
        try {
            int numLicenca = Integer.parseInt(licencaTexto);
            if (numLicenca <= 0) throw new Exception("Licença deve ser positiva.");
            if (capitaoDAO.existe(numLicenca)) throw new Exception("Já existe capitão com esta licença!");
            
            capitaoDAO.salvar(new Capitao(nome, numLicenca, contato));
        } catch (NumberFormatException e) {
            throw new Exception("A licença deve ser numérica.");
        }
    }

    public void removerCapitao(Capitao capitao) throws Exception {
        if (capitao == null) throw new Exception("Selecione um capitão.");
        capitaoDAO.deletar(capitao.getNumLicenca());
    }

    public void atualizarCapitao(Capitao capitao, String nome, String licencaTxt, String contato) throws Exception {
        if (capitao == null) throw new Exception("Selecione um capitão.");
        int novaLicenca = Integer.parseInt(licencaTxt);
        int antigaLicenca = capitao.getNumLicenca();
        
        if (novaLicenca != antigaLicenca && capitaoDAO.existe(novaLicenca)) {
            throw new Exception("Esta nova licença já está em uso.");
        }
        
        capitao.setNome(nome);
        capitao.setNumLicenca(novaLicenca);
        capitao.setContato(contato);
        capitaoDAO.atualizar(capitao, antigaLicenca);
    }

    public String gerarRelatorioCapitaes() {
        try {
            List<Capitao> lista = capitaoDAO.listarTodos();
            if (lista.isEmpty()) return "Nenhum capitão cadastrado.";
            StringBuilder sb = new StringBuilder();
            for (Capitao c : lista) {
                sb.append("Nome: ").append(c.getNome()).append(" | Licença: ").append(c.getNumLicenca()).append("\n");
            }
            return sb.toString();
        } catch (Exception e) { return "Erro: " + e.getMessage(); }
    }

    public Capitao[] getArrayCapitaes() {
        try { return capitaoDAO.listarTodos().toArray(new Capitao[0]); } 
        catch (Exception e) { return new Capitao[0]; }
    }
    
    public boolean temCapitaes() { return getArrayCapitaes().length > 0; }

    // ========================================================================
    // VALIDAÇÕES DA ECLUSA (ABRIR, ENCHER, SECAR)
    // ========================================================================
    
    public void validarAbertura() throws Exception {
        if (!model.getStatus().equals("seca") && !model.getStatus().equals("cheia")) 
            throw new Exception("Aguarde a eclusa terminar de encher ou secar.");
    }
    
    public void validarEnchimento() throws Exception {
        if (model.getPorta()) throw new Exception("Feche a porta antes de encher.");
        if (model.getStatus().equals("cheia")) throw new Exception("A eclusa já está cheia.");
    }
    
    public void validarSecagem() throws Exception {
        if (model.getPorta()) throw new Exception("Feche a porta antes de secar.");
        if (model.getStatus().equals("seca")) throw new Exception("A eclusa já está seca.");
    }
    
    public void validarAtualizacaoEclusa(float c, float l, float max, float min, float v) throws Exception {
         if (c < 0 || l < 0 || max < 0 || min < 0 || v < 0) throw new Exception("Sem valores negativos.");
         if (min > max) throw new Exception("Mínimo maior que Máximo.");
    }
}