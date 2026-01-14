package controller;

import com.mycompany.view.*;
import model.Eclusa;
import model.Embarcacao;
import model.Capitao;
import service.EclusaService;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class EclusaController {
    
    private Eclusa model;
    private EclusaService service; 
    private View view;
    
    // Views filhas
    private BalsaView balsaView;
    private PetroleiroView petroView;
    private TurismoView turiView;
    private CargueiroView cargView;
    private RemoverEmbarcacao removeView;
    private EmbarEclusaView tela = new EmbarEclusaView(this);
    private AtualizarView telaAtualizar = new AtualizarView(this);
    private PrecoView telaPreco = new PrecoView(this);
    private CapitaoView capitaoView = new CapitaoView(this);
    private GerenciarCapitaoView gerenCapView = new GerenciarCapitaoView(this);
    private ExcluirCapView excluirCapView = new ExcluirCapView(this);
    private AtualizarCapitaoView atualizarCapView = new AtualizarCapitaoView(this);

    public EclusaController(){}
    
    public EclusaController(Eclusa model, View view){
        this.model = model;
        this.view = view;
        this.service = new EclusaService(model); 
        this.atualizarDadosNaTela();
    }

    public void evento(java.awt.event.ActionEvent evt) {
        String comando = evt.getActionCommand();

        switch (comando) {
            // --- CONTROLES DA ECLUSA ---
            case "Abrir": this.abrir(); break;
            case "Fechar": this.fechar(); break;
            case "Secar": this.secar(); break;
            case "Encher": this.encher(); break;
            case "Atualizar Dados Eclusa": this.atualizarEclusa(); break;
            case "Novo Dia": this.novoDia();break;
            
            // --- NAVEGAÇÃO ENTRE JANELAS ---
            case "ExcluirCap": this.telaExcluirCap(); break;
            case "Atualizar Eclusa": this.telaAtualizarEclusa(); break;
            case "Atualizar Preços": this.telaAtualizarPrecos(); break;
            case "Editar": this.telaEditar(); break;
            case "Cadastrar Novo": this.telaCapitao(); break;
            case "Gerenciar Capitães": this.telaGerenCap(); break;
            case "Cargueiro": this.abrirViewCargueiro(); break;
            case "Petroleiro": this.abrirViewPetroleiro(); break;
            case "Turismo": this.abrirViewTurismo(); break;
            case "Balsa": this.abrirViewBalsa(); break;
            
            // Botões Voltar
            case "VoltarBalsa": if(balsaView!=null) balsaView.setVisible(false); break;
            case "VoltarCarg": if(cargView!=null) cargView.setVisible(false); break;
            case "VoltarTuri": if(turiView!=null) turiView.setVisible(false); break;
            case "VoltarPetro": if(petroView!=null) petroView.setVisible(false); break;
            case "Voltar": this.voltar(); break;
            case "VoltarCap": capitaoView.setVisible(false); break;
            case "VoltarPreco": telaPreco.setVisible(false); break;
            case "VoltarEclusa": telaAtualizar.setVisible(false); break;
            case "VoltarGereCap": gerenCapView.setVisible(false); break;
            case "VoltarExcluir": excluirCapView.setVisible(false); break;
            case "VoltarEditar": atualizarCapView.setVisible(false); break;

            // --- AÇÕES DO CRUD DE CAPITÃO (Delegando para o Service) ---
            
            case "Cadastrar": 
                try {
                    // Chama o Service para validar e salvar
                    service.cadastrarCapitao(
                        capitaoView.getNome(), 
                        capitaoView.getNumLicenca(), 
                        capitaoView.getContato()
                    );
                    
                    this.atualizarListasNasViews(); // Atualiza a UI
                    
                    JOptionPane.showMessageDialog(capitaoView, "Capitão cadastrado com sucesso!");
                    capitaoView.limparCampos();
                    gerenCapView.setListaCaptaes(service.gerarRelatorioCapitaes());
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(capitaoView, e.getMessage());
                }
                break;

            case "ExcluirCapitao":
                try {
                    Capitao selecionado = excluirCapView.getCapitaoSelecionado();
                    service.removerCapitao(selecionado); // Service remove
                    
                    this.atualizarListasNasViews(); // UI atualiza
                    gerenCapView.setListaCaptaes(service.gerarRelatorioCapitaes());
                    
                    JOptionPane.showMessageDialog(excluirCapView, "Capitão removido!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(excluirCapView, "Erro: " + e.getMessage());
                }
                break;

            case "AtualizarCap":
                try {
                    Capitao selecionado = atualizarCapView.getCapitaoSelecionado();
                    service.atualizarCapitao(
                        selecionado, 
                        atualizarCapView.getNome(), 
                        atualizarCapView.getNumLicenca(), 
                        atualizarCapView.getContato()
                    );
                    
                    this.atualizarListasNasViews();
                    gerenCapView.setListaCaptaes(service.gerarRelatorioCapitaes());
                    atualizarCapView.limparCampos();
                    
                    JOptionPane.showMessageDialog(atualizarCapView, "Atualizado com sucesso!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(atualizarCapView, "Erro: " + e.getMessage());
                }
                break;

            //AÇÕES DE EMBARCAÇÃO
            
            case "addBalsa": this.adicionarEmbarcacao("balsa", balsaView); break;
            case "addCargueiro": this.adicionarEmbarcacao("cargueiro", cargView); break;
            case "addPetroleiro": this.adicionarEmbarcacao("petroleiro", petroView); break;
            case "addTurismo": this.adicionarEmbarcacao("turismo", turiView); break;
            
            case "AtualizarPreco": this.atualizarPreco(); break;
            case "Atualizar": this.atualizarEclusa(); break;
            
            // Outros
            case "EmbarcacaonaEclusa": this.mostrarEmbarcacao(); break;
            case "Remover da Fila": this.abrirRemove(); break;
            case "SIM": this.remove(); break;
            case "NÂO": this.naoRemoverEmbarcacao(); break;

            default: System.out.println("Comando: " + comando);
        }
    }

    // --- MÉTODOS AUXILIARES E DE UI ---

    // Método para os testes chamarem, caso necessário
    public void cadastrarCapitao(String nome, String licenca, String contato) throws Exception {
        service.cadastrarCapitao(nome, licenca, contato);
    }
    
    public String listarCapitaes() {
        return service.gerarRelatorioCapitaes();
    }
    
    // Método auxiliar unificado para atualizar todas as ComboBoxes
    private void atualizarListasNasViews() {
        Capitao[] lista = service.getArrayCapitaes();
        
        if (excluirCapView != null) excluirCapView.atualizarComboCapitaes(lista);
        if (atualizarCapView != null) atualizarCapView.atualizarComboCapitaes(lista);
        if (balsaView != null) balsaView.atualizarComboCapitaes(lista);
        if (cargView != null) cargView.atualizarComboCapitaes(lista);
        if (turiView != null) turiView.atualizarComboCapitaes(lista);
        if (petroView != null) petroView.atualizarComboCapitaes(lista);
    }

    // --- ABRIR JANELAS (Carregando listas do Service) ---

    public void telaCapitao() {
        capitaoView.setVisible(true);
    }
    
    public void telaGerenCap() {
        gerenCapView.setListaCaptaes(service.gerarRelatorioCapitaes());
        gerenCapView.setVisible(true);
    }
    
    public void telaExcluirCap() {
        this.atualizarListasNasViews();
        excluirCapView.setVisible(true);
    }

    public void telaEditar() {
        this.atualizarListasNasViews();
        atualizarCapView.setVisible(true);
    }

    // Métodos abrirView
    public void abrirViewBalsa() {
        balsaView = new BalsaView(this);
        if (service.temCapitaes()) balsaView.atualizarComboCapitaes(service.getArrayCapitaes());
        balsaView.setVisible(true);
    }
    
    public void abrirViewCargueiro(){ 
        cargView=new CargueiroView(this); 
        if(service.temCapitaes()) cargView.atualizarComboCapitaes(service.getArrayCapitaes()); 
        cargView.setVisible(true); 
    }
    public void abrirViewPetroleiro(){ 
        petroView=new PetroleiroView(this); 
        if(service.temCapitaes()) petroView.atualizarComboCapitaes(service.getArrayCapitaes()); 
        petroView.setVisible(true); 
    }
    public void abrirViewTurismo(){ 
        turiView=new TurismoView(this); 
        if(service.temCapitaes()) turiView.atualizarComboCapitaes(service.getArrayCapitaes()); 
        turiView.setVisible(true); 
    }

    // --- LÓGICA DE ADICIONAR EMBARCAÇÃO 

    private void adicionarEmbarcacao(String tipo, java.awt.Window viewAtual) {
        try {
            // Variáveis para coletar da UI
            float comp=0, larg=0; String ori="", dest="", pais="", sent=""; int ci=0; Capitao cap=null;
            Object[] extras = new Object[0];

            // Coleta dados específicos dependendo de qual tela está aberta
            if (tipo.equals("balsa")) {
                comp = balsaView.getComprimento(); larg = balsaView.getLargura();
                ori = balsaView.getOrigem(); dest = balsaView.getDestino();
                pais = balsaView.getPais(); ci = balsaView.getCodigoIdentificacao();
                cap = balsaView.getCapitaoSelecionado(); sent = balsaView.getSentido();
                extras = new Object[]{balsaView.getCarga(), balsaView.getPeso()};
            } else if (tipo.equals("cargueiro")) {
                comp = cargView.getComprimento(); larg = cargView.getLargura();
                ori = cargView.getOrigem(); dest = cargView.getDestino();
                pais = cargView.getPais(); ci = cargView.getCodigoIdentificacao();
                cap = cargView.getCapitaoSelecionado(); sent = cargView.getSentido();
                extras = new Object[]{cargView.getContainers()};
            } else if (tipo.equals("petroleiro")) {
                comp=petroView.getComprimento(); larg=petroView.getLargura(); ori=petroView.getOrigem(); dest=petroView.getDestino();
                pais=petroView.getPais(); ci=petroView.getCodigoIdentificacao(); cap=petroView.getCapitaoSelecionado(); sent=petroView.getSentido();
                extras = new Object[]{petroView.getLitros()};
            } else if (tipo.equals("turismo")) {
                comp=turiView.getComprimento(); larg=turiView.getLargura(); ori=turiView.getOrigem(); dest=turiView.getDestino();
                pais=turiView.getPais(); ci=turiView.getCodigoIdentificacao(); cap=turiView.getCapitaoSelecionado(); sent=turiView.getSentido();
                extras = new Object[]{turiView.getPassageiros(), turiView.getCabine()};
            }

            // O Controller apenas passa os dados brutos para o Service
            service.adicionarEmbarcacao(tipo, comp, larg, ori, dest, pais, ci, cap, sent, extras);

            // Atualiza View Principal
            view.atualizaFila(model.tamanhoFila(), model.tempo());
            view.atualizartotalApurado();
            
            // Fecha janela
            if (viewAtual != null) viewAtual.setVisible(false);
            
            // Atualiza sentido na tela principal
            String sentidoProx = "Fila vazia";
            if (model.tamanhoFila() > 0) {
                 sentidoProx = model.getFila().get(0).getSentido().equals("Subir") ? "RIO--->MAR" : "MAR--->RIO";
            }
            view.sentidoProxima(sentidoProx);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(viewAtual, "Erro: " + e.getMessage());
        }
    }

    // --- OPERAÇÕES DA ECLUSA (Chama Service para validar) ---

    public void abrir() {
        try {
            // Só abre se estiver estável (Seca ou Cheia)
            if (model.getStatus().equals("seca") || model.getStatus().equals("cheia")) {
                model.setPorta(true);
                view.recado("Eclusa aberta");

                // SE NÃO TEM NINGUÉM NA ECLUSA (Tenta Entrar)
                if (model.getOcupada() == false) {
                    if (model.tamanhoFila() > 0) {
                        Embarcacao proxima = model.getFila().get(0);

                        // Regra: Subir na Seca
                        if (proxima.getSentido().equals("Subir") && model.getStatus().equals("seca")) {
                            view.sentido("RIO--->MAR");
                            
                            // Adicionei validação do service aqui por segurança
                            service.validarEntrada(proxima, model.getStatus()); 
                            
                            proxima.entrar(model);
                            view.atualizaFila(model.tamanhoFila(), model.tempo());
                            
                            if (model.tamanhoFila() == 0) view.sentidoProxima("Fila vazia");
                        } 
                        // Regra: Descer na Cheia
                        else if (proxima.getSentido().equals("Descer") && model.getStatus().equals("cheia")) {
                            view.sentido("RIO<---MAR");
                            
                            service.validarEntrada(proxima, model.getStatus());
                            
                            proxima.entrar(model);
                            view.atualizaFila(model.tamanhoFila(), model.tempo());
                            
                            if (model.tamanhoFila() == 0) view.sentidoProxima("Fila vazia");
                        } 
                        // Nível errado para entrar
                        else {
                            if (proxima.getSentido().equals("Subir")) view.sentidoProxima("RIO--->MAR");
                            else view.sentidoProxima("RIO<---MAR");
                        }
                    } else {
                        view.sentidoProxima("Fila vazia");
                    }
                } 
                // SE JÁ TEM ALGUÉM NA ECLUSA (Tenta Sair)
                else {
                    Embarcacao naEclusa = model.getEmbarcacaoNaEclusa();
                    
                    // Verifica se pode sair (Subir na Cheia / Descer na Seca)
                    if ((naEclusa.getSentido().equals("Subir") && model.getStatus().equals("cheia")) ||
                        (naEclusa.getSentido().equals("Descer") && model.getStatus().equals("seca"))) {
                        
                        // --- A CORREÇÃO ESTÁ AQUI EMBAIXO ---
                        // 1. Removemos do Banco de Dados pelo ID
                        service.removerEmbarcacaoDoBanco(naEclusa.getCodigoDeIdentificacao());
                        
                        // 2. Só depois removemos da memória (Sair)
                        naEclusa.sair(model);
                        
                        view.sentido(" ");
                        view.recado("Embarcação saiu e foi removida do banco!");
                    }
                }
                
                view.atualizaFila(model.tamanhoFila(), model.tempo());
                // Força atualização visual geral para garantir
                view.atualizartotalApurado();
                
            } else {
                view.recado("Aguarde o nível estabilizar para abrir.");
            }
        } catch (Exception ex) {
            view.recado("Erro: " + ex.getMessage());
        }
    }
    public void fechar(){
        if (model.getStatus().equals("seca")||model.getStatus().equals("cheia")){
            model.setPorta(false);
            view.recado("Eclusa fechada");
            view.atualizaFila(model.tamanhoFila(),model.tempo());
        }
    }
    
    public void encher(){
        if(model.getPorta()==false&&model.getStatus().equals("seca")){
            model.setStatus("Enchendo");
            this.carregarBarra();
            view.status(model.getStatus());
            view.atualizaFila(model.tamanhoFila(),model.tempo());
            
        }else if(model.getStatus().equals("Enchendo")){
            view.recado("A Eclusa já está enchendo");
        }else if(model.getStatus().equals("Secando")){
            view.recado("A Eclusa está secando nesse momento");
        }else if(model.getPorta()==false&&model.getStatus().equals("cheia")){
            view.recado("A Eclusa ja esta cheia");
        }
        else{
            view.recado("Feche a eclusa primeiro para encher ");
        }
    }
    public  void secar(){
        if(model.getPorta()==false&&model.getStatus().equals("cheia")){
            model.setStatus("Secando");
            this.carregarBarra();
            view.status(model.getStatus());
            view.atualizaFila(model.tamanhoFila(),model.tempo());
        }else if(model.getStatus().equals("Secando")){
            view.recado("A Eclusa já está Secando");
        }else if(model.getStatus().equals("Enchendo")){
            view.recado("A Eclusa está enchendo nesse momento");
        }
        else if(model.getPorta()==false&&model.getStatus().equals("seca")){
            view.recado("A Eclusa ja esta seca");
        }else{
            view.recado("feche a eclusa primeiro para secar");
        }
    }

    // A barra de progresso lida diretamente com Swing, mantém no Controller
    public void carregarBarra(){
        final float tempoTotal = model.tempo()*60000;
        final float intervalo = tempoTotal / 100; 

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep((long) intervalo);
                        final int valor = i; 
                        if(valor==100){
                            view.status(model.getStatus());
                            if(model.getStatus().equals("Enchendo")){
                                model.setStatus("cheia");
                                view.status("Cheia");
                            }else{
                                view.status("Seca");
                                model.setStatus("seca");
                            }
                            
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                view.barra(valor);
                            }
                        });

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
    // --- ATUALIZAÇÕES GERAIS ---
    
    public void atualizarEclusa(){
         try {
            float c = Float.parseFloat(telaAtualizar.getCampoComprimento());
            float l = Float.parseFloat(telaAtualizar.getCampoLargura());
            float max = Float.parseFloat(telaAtualizar.getCampoCapacidadeMaxima());
            float min = Float.parseFloat(telaAtualizar.getCampoCapacidadeMinima());
            float v = Float.parseFloat(telaAtualizar.getCampoVazao());
            
            service.validarAtualizacaoEclusa(c, l, max, min, v);
            
            // Se passou, atualiza Model (ou poderia ter um método no service pra setar tudo)
            model.getTamanho().setComprimento(c);
            model.getTamanho().setLargura(l);
            model.setCapacidadeMaxima(max);
            model.setCapacidadeMinima(min);
            model.setVazao(v);
            
            telaAtualizar.atuaTempo(model.tempo());
            view.atualizaFila(model.tamanhoFila(),model.tempo());
            
         } catch(Exception e) { view.mostrarErro(); }
    }
    
    public void atualizarPreco() {
         try {
            // Pega valores da View...
            float carg = Float.parseFloat(telaPreco.getCampoPrecoCargueiro());
            // ... (pegar outros valores) ...
            
            if (carg < 0) { // Validação simples
                JOptionPane.showMessageDialog(telaPreco, "Valores negativos!");
            } else {
                telaPreco.setAtualizou();
                JOptionPane.showMessageDialog(telaPreco, "Atualizado!");
            }
         } catch (Exception e) {
             telaPreco.setNaoAtualizou();
             JOptionPane.showMessageDialog(telaPreco, "Erro de formato");
         }
    }
    
    // --- VISUALIZAÇÃO E REMOÇÃO ---
    
    public void voltar(){ tela.setVisible(false); }
    public void naoRemoverEmbarcacao(){ removeView.setVisible(false); }
    
    public void mostrarEmbarcacao(){ 
        if(model.getEmbarcacaoNaEclusa()!=null) {
            String msg = model.getEmbarcacaoNaEclusa().mensagemEmbarcacao();
            tela.textoEmbarcacao(msg);
            tela.setVisible(true);
        }
    }
    
    public void abrirRemove(){
         if(model.tamanhoFila()==0) JOptionPane.showMessageDialog(view, "Fila Vazia");
         else {
             removeView = new RemoverEmbarcacao(this);
             this.mostrarEmbarcacao(true);
             removeView.setVisible(true);
         }
    }
    
    public void remove(){
        if(model.tamanhoFila()>0){
            view.atualizartotalApuradoRemove();
            view.atualizaFila(model.tamanhoFila(),model.tempo());
            if(model.tamanhoFila()==0) view.sentidoProxima("Fila vazia");
            removeView.setVisible(false);
            JOptionPane.showMessageDialog(view, "A última embarcação saiu da fila");
        }
    }
    
    public void mostrarEmbarcacao(boolean remove) {
        if(model.getUltimaNaFila()!=null){
             String msg = model.getUltimaNaFila().mensagemEmbarcacao();
             removeView.textoEmbarcacao(msg);
        }
    }
    
    public void telaAtualizarEclusa(){ telaAtualizar.setVisible(true); }
    public void telaAtualizarPrecos(){ telaPreco.setVisible(true); }
    
    private void atualizarDadosNaTela() {

        view.atualizaFila(model.tamanhoFila(), model.tempo()); 
        view.atualizartotalApurado();
        view.status(model.getStatus());

        if (model.tamanhoFila() > 0) {
            String sentidoTecnico = model.getFila().get(0).getSentido();
            String sentidoVisual = traduzirSentido(sentidoTecnico); // Usa o tradutor
            view.sentidoProxima(sentidoVisual);
        } else {
            view.sentidoProxima("---");
        }
        
        if (model.getEmbarcacaoNaEclusa() != null) {
            view.recado("Ocupada por: " + model.getEmbarcacaoNaEclusa().tipoEmbarcacao());
            
            String sentidoTecnico = model.getEmbarcacaoNaEclusa().getSentido();
            view.sentido(traduzirSentido(sentidoTecnico)); // Usa o tradutor
        } else {
            view.sentido("---");
        }
        
        view.barra(0);
    }

    private String traduzirSentido(String sentido) {
        if (sentido == null) return "---";
        if (sentido.equalsIgnoreCase("Subir")) {
           return "RIO--->MAR";
        } else if (sentido.equalsIgnoreCase("Descer")) {
            return "RIO<---MAR"; 
 
        }
        return sentido;
    }
    public void novoDia() {
        // 1. Zera o valor no Model
        model.iniciarNovoDia();

        // 3. Atualiza o texto do valor (R$ 0.00)
        view.atualizartotalApurado();
    }
}