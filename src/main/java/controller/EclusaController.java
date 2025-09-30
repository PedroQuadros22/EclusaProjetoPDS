package controller;

import com.mycompany.view.BalsaView;
import com.mycompany.view.CargueiroView;
import com.mycompany.view.EmbarEclusaView;
import com.mycompany.view.PetroleiroView;
import com.mycompany.view.RemoverEmbarcacao;
import com.mycompany.view.TurismoView;
import model.Eclusa;
import com.mycompany.view.View;
import exception.TamanhoIncompativelException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Balsa;
import model.Cargueiro;
import model.NavioTuristico;
import model.Capitao;
import model.Petroleiro;
import model.Tamanho;

public class EclusaController {
    private Eclusa model;
    private View view;
    private BalsaView balsaView;
    private PetroleiroView petroView;
    private TurismoView turiView;
    private CargueiroView cargView;
    private RemoverEmbarcacao removeView;
    private EmbarEclusaView tela= new EmbarEclusaView(this);

    public EclusaController(){}
    public EclusaController(Eclusa model, View view){
        this.model=model;
        this.view=view;
    }
    public void evento (java.awt.event.ActionEvent evt){
        String comando = evt.getActionCommand();
        if ("Abrir".equals(comando)) {
            this.abrir();
        }if("Fechar".equals(comando)){
            this.fechar();
        }if("Cargueiro".equals(comando)){
            this.addCargueiro();
        }if("Petroleiro".equals(comando)){
            this.addPetroleiro();
        }if("Turismo".equals(comando)){
            this.addTurismo();
        }if("Balsa".equals(comando)){
            this.addBalsa();
        }if("Secar".equals(comando)){
            this.secar();
        }if("Encher".equals(comando)){
            this.encher();
        }if("EmbarcacaonaEclusa".equals(comando)){
            this.mostrarEmbarcacao();
        }if("Atualizar Eclusa".equals(comando)){
            this.atualizarEclusa();
        }if("VoltarBalsa".equals(comando)){
            this.voltarBalsa();
        }if("VoltarCarg".equals(comando)){
            this.voltarCarg();
        }if("VoltarTuri".equals(comando)){
            this.voltarTuri();
        }if("VoltarPetro".equals(comando)){
            this.voltarPetro();
        }if("addBalsa".equals(comando)){
            this.adicionarBalsa();
        }if("addCargueiro".equals(comando)){
            this.adicionarCargueiro();
        }if("addTurismo".equals(comando)){
            this.adicionarTurismo();
        }if("addPetroleiro".equals(comando)){
            this.adicionarPetroleiro();
        }if("Voltar".equals(comando)){
            this.voltar();
        }if("SIM".equals(comando)){
            this.remove();
        }if("NÂO".equals(comando)){
            this.naoRemoverEmbarcacao();
        }if("Remover da Fila".equals(comando)){
            this.abrirRemove();
        }
    }
    
    public void voltar(){
        tela.setVisible(false);
    }
    
    public void naoRemoverEmbarcacao(){
        removeView.setVisible(false);
    }
    
    public void abrirRemove(){
        if(model.tamanhoFila()==0){
            JOptionPane.showMessageDialog(view, "Fila Vazia");
        }else{
            removeView= new RemoverEmbarcacao(this);
            this.mostrarEmbarcacao(true);
            removeView.setVisible(true);
        }
    }
    
    
    public void remove(){
        if(model.tamanhoFila()>0){
            view.atualizartotalApuradoRemove();
            view.atualizaFila(model.tamanhoFila(),model.tempo());
            if(model.tamanhoFila()==0){
                view.sentidoProxima("Fila vazia");
            }
            removeView.setVisible(false);
            JOptionPane.showMessageDialog(view, "A última embarcação saiu da fila");
        }
    }
    
    public void voltarBalsa(){
        balsaView.setVisible(false);
    }
    public void voltarPetro(){
        petroView.setVisible(false);
    }public void voltarTuri(){
        turiView.setVisible(false);
    }public void voltarCarg(){
        cargView.setVisible(false);
    }
    
    public void adicionarCargueiro(){
        try {
            if (cargView.getComprimento() < 0 || cargView.getLargura() < 0 || 
                cargView.getContainers() < 0 || cargView.getCodigoIdentificacao() < 0) {
                JOptionPane.showMessageDialog(cargView, "Por favor, insira valores numéricos positivos.");
            } else {
                if (cargView.getOrigem().isEmpty() || cargView.getDestino().isEmpty() || 
                    cargView.getPais().isEmpty() || cargView.getCapitao().isEmpty() || 
                    cargView.getSentido().isEmpty()) {
                    JOptionPane.showMessageDialog(cargView, "Por favor, preencha todos os campos.");
                } else {
                    this.addCargueiro(cargView.getComprimento(), cargView.getLargura(), 
                                      cargView.getOrigem(), cargView.getDestino(), 
                                      cargView.getPais(), cargView.getCodigoIdentificacao(), 
                                      cargView.getCapitao(), cargView.getSentido(), 
                                      cargView.getContainers());
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(cargView, "Por favor, insira valores numéricos válidos nos campos de número.");
        }
    }

    public void adicionarTurismo(){
        try {
            if (turiView.getComprimento() < 0 || turiView.getLargura() < 0 || 
                turiView.getPassageiros() < 0 || turiView.getCabine() < 0 || 
                turiView.getCodigoIdentificacao() < 0) {
                JOptionPane.showMessageDialog(turiView, "Por favor, insira valores numéricos positivos.");
            } else {
                if (turiView.getOrigem().isEmpty() || turiView.getDestino().isEmpty() || 
                    turiView.getPais().isEmpty() || turiView.getCapitao().isEmpty() || 
                    turiView.getSentido().isEmpty()) {
                    JOptionPane.showMessageDialog(turiView, "Por favor, preencha todos os campos.");
                } else {
                    this.addTurismo(turiView.getComprimento(), turiView.getLargura(), 
                                     turiView.getOrigem(), turiView.getDestino(), 
                                     turiView.getPais(), turiView.getCodigoIdentificacao(), 
                                     turiView.getCapitao(), turiView.getSentido(), 
                                     turiView.getPassageiros(), turiView.getCabine());
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(turiView, "Por favor, insira valores numéricos válidos nos campos de número.");
        }
    }

    
    public void adicionarPetroleiro(){
        try {
            if (petroView.getComprimento() < 0 || petroView.getLargura() < 0 || 
                petroView.getLitros() < 0 || petroView.getCodigoIdentificacao() < 0) {
                JOptionPane.showMessageDialog(petroView, "Por favor, insira valores numéricos positivos.");
            } else {
                if (petroView.getOrigem().isEmpty() || petroView.getDestino().isEmpty() || 
                    petroView.getPais().isEmpty() || petroView.getCapitao().isEmpty() || 
                    petroView.getSentido().isEmpty()) {
                    JOptionPane.showMessageDialog(petroView, "Por favor, preencha todos os campos.");
                } else {
                    this.addPetroleiro(petroView.getComprimento(), petroView.getLargura(), 
                                        petroView.getOrigem(), petroView.getDestino(), 
                                        petroView.getPais(), petroView.getCodigoIdentificacao(), 
                                        petroView.getCapitao(), petroView.getSentido(), 
                                        petroView.getLitros());
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(petroView, "Por favor, insira valores numéricos válidos nos campos de número.");
        }
    }

    public void adicionarBalsa(){
    try {
        if (balsaView.getComprimento() < 0 || balsaView.getLargura() < 0 || 
            balsaView.getPeso() < 0 || balsaView.getCodigoIdentificacao() < 0) {
            JOptionPane.showMessageDialog(balsaView, "Por favor, insira valores numéricos positivos.");
        } else {
            if (balsaView.getOrigem().isEmpty() || balsaView.getDestino().isEmpty() ||
                balsaView.getPais().isEmpty() || balsaView.getCapitao().isEmpty() ||
                balsaView.getSentido().isEmpty() || balsaView.getCarga().isEmpty()) {
                JOptionPane.showMessageDialog(balsaView, "Por favor, preencha todos os campos.");
            } else {
                this.addBalsa(balsaView.getComprimento(), balsaView.getLargura(), 
                               balsaView.getOrigem(), balsaView.getDestino(), 
                               balsaView.getPais(), balsaView.getCodigoIdentificacao(), 
                               balsaView.getCapitao(), balsaView.getSentido(), 
                               balsaView.getCarga(), balsaView.getPeso());
            }
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(balsaView, "Por favor, insira valores numéricos válidos nos campos de número.");
    }
}

    public void atualizarEclusa(){
        try {
            float comprimentoValor = Float.parseFloat(view.getCampoComprimento());
            float larguraValor = Float.parseFloat(view.getCampoLargura());
            float capacidadeMaximaValor = Float.parseFloat(view.getCampoCapacidadeMaxima());
            float capacidadeMinimaValor = Float.parseFloat(view.getCampoCapacidadeMinima());
            float vazaoValor = Float.parseFloat(view.getCampoVazao());
            float precoCargueiroValor = Float.parseFloat(view.getCampoPrecoCargueiro());
            float precoPetroleiroValor = Float.parseFloat(view.getCampoPrecoPetroleiro());
            float precoTurismoValor = Float.parseFloat(view.getCampoPrecoTurismo());
            float precoBalsaValor = Float.parseFloat(view.getCampoPrecoBalsa());

            if (comprimentoValor < 0 || larguraValor < 0 || capacidadeMaximaValor < 0 || capacidadeMinimaValor < 0 ||
                vazaoValor < 0 || precoCargueiroValor < 0 || precoPetroleiroValor < 0 || precoTurismoValor < 0 || precoBalsaValor < 0) {
                view.mostrarErro();
            } else {
                this.atualizarEclusa(view.getCampoComprimento(), view.getCampoLargura(), view.getCampoCapacidadeMaxima(),
                        view.getCampoCapacidadeMinima(), view.getCampoVazao(), view.getCampoPrecoCargueiro(),
                        view.getCampoPrecoPetroleiro(), view.getCampoPrecoTurismo(), view.getCampoPrecoBalsa());
                view.atuaTempo(model.tempo());
                view.atualizaFila(model.tamanhoFila(),model.tempo());
            }
        } catch (NumberFormatException e) {
            view.mostrarErro();
        }
    }

    public void addPetroleiro(){
        petroView=new PetroleiroView(this);
        petroView.setVisible(true);
    }
    
    public void addPetroleiro(float comprimento, float largura, String origem, String destino, String pais, int cI, String nome, String sentido, float litros) {
        Capitao capitao= new Capitao(nome);
        Tamanho tamanho= new Tamanho(comprimento,largura);
        try {
            tamanho.cabeNaEclusa(model.getTamanho());
            Petroleiro petroleiro=new Petroleiro(tamanho,origem,destino,pais,cI,capitao,sentido,litros);
            if(!model.setEmbarcacoes(cI, petroleiro)){
                JOptionPane.showMessageDialog(petroView, "Já tem um Navio Petroleiro na fila com esse Código de identificação");
                return;
            }
            view.atualizaFila(model.tamanhoFila(),model.tempo());
            view.atualizartotalApurado();
            petroView.setVisible(false);
            if(model.getFila().get(0).getSentido().equals("Subir")){
                view.sentidoProxima("RIO--->MAR");
            }else{
                view.sentidoProxima("MAR--->RIO");
            }
        } catch (TamanhoIncompativelException e) {
            petroView.mostrarErro();
        }

        
    }
    
    public void addBalsa(){
        balsaView=new BalsaView(this);
        balsaView.setVisible(true);
    }
    public void addBalsa(float comprimento, float largura, String origem, String destino, String pais, int cI, String nome, String sentido, String carga,float peso) {
        Capitao capitao= new Capitao(nome);
        Tamanho tamanho= new Tamanho(comprimento,largura);
        try {
            tamanho.cabeNaEclusa(model.getTamanho());
            Balsa balsa=new Balsa(tamanho,origem,destino,pais,cI,capitao,sentido,carga,peso);
            if(!model.setEmbarcacoes(cI, balsa)){
                JOptionPane.showMessageDialog(balsaView, "Já tem uma balsa na fila com esse Código de identificação");
                return;
            }
            view.atualizaFila(model.tamanhoFila(),model.tempo());
            view.atualizartotalApurado();
            balsaView.setVisible(false);
            if(model.getFila().get(0).getSentido().equals("Subir")){
                view.sentidoProxima("RIO--->MAR");
            }else{
                view.sentidoProxima("MAR--->RIO");
            }
        } catch (TamanhoIncompativelException e) {
            balsaView.mostrarErro();
        }
    }
    
    
    public void addCargueiro(){
        cargView=new CargueiroView(this);
        cargView.setVisible(true);
    }
    public void addCargueiro(float comprimento, float largura, String origem, String destino, String pais, int cI, String nome, String sentido, int conteineres) {
        Capitao capitao= new Capitao(nome);
        Tamanho tamanho= new Tamanho(comprimento,largura);
        try {
            tamanho.cabeNaEclusa(model.getTamanho());
            Cargueiro cargueiro=new Cargueiro(tamanho,origem,destino,pais,cI,capitao,sentido,conteineres);
            if(!model.setEmbarcacoes(cI, cargueiro)){
                JOptionPane.showMessageDialog(cargView, "Já tem um Navio Cargueiro na fila com esse Código de identificação");
                return;
            }
            view.atualizaFila(model.tamanhoFila(),model.tempo());
            view.atualizartotalApurado();
            cargView.setVisible(false);
            if(model.getFila().get(0).getSentido().equals("Subir")){
                view.sentidoProxima("RIO--->MAR");
            }else{
                view.sentidoProxima("MAR--->RIO");
            }
        } catch (TamanhoIncompativelException e) {
            cargView.mostrarErro();
        }
    }
    
    public void addTurismo(){
        turiView=new TurismoView(this);
        turiView.setVisible(true);
    }
    public void addTurismo(float comprimento, float largura, String origem, String destino, String pais, int cI, String nome, String sentido, int passageiros,int cabine) {
        Capitao capitao= new Capitao(nome);
        Tamanho tamanho= new Tamanho(comprimento,largura);
        try {
            tamanho.cabeNaEclusa(model.getTamanho());
            NavioTuristico turismo=new NavioTuristico(tamanho,origem,destino,pais,cI,capitao,sentido,passageiros,cabine);
            if(!model.setEmbarcacoes(cI, turismo)){
                JOptionPane.showMessageDialog(turiView, "Já tem um Navio Turistico na fila com esse Código de identificação");
                return;
            }
            view.atualizaFila(model.tamanhoFila(),model.tempo());
            view.atualizartotalApurado();
            turiView.setVisible(false);
            if(model.getFila().get(0).getSentido().equals("Subir")){
                view.sentidoProxima("RIO--->MAR");
            }else{
                view.sentidoProxima("MAR--->RIO");
            }
        } catch (TamanhoIncompativelException e) {
            turiView.mostrarErro();
        }
    }
    public void atualizarEclusa(String comprimento, String largura, String capacidadeMaxima, String capacidadeMinima,
            String vazao, String precoCargueiro, String precoPetroleiro, String precoTurismo, String precoBalsa) {
        if(Float.parseFloat(comprimento)!=model.getTamanho().getComprimento()){
            model.getTamanho().setComprimento(Float.parseFloat(comprimento));
        }
        if(Float.parseFloat(largura)!=model.getTamanho().getLargura()){
            model.getTamanho().setLargura(Float.parseFloat(largura));
        }
        if(Float.parseFloat(capacidadeMaxima)!=model.getCapacidadeMaxima()){
            if(Float.parseFloat(capacidadeMaxima)<model.getCapacidadeMinima()){
                JOptionPane.showMessageDialog(view, "Capacidade Maxima não pode ser menor que a Minima");
            }else{
                model.setCapacidadeMaxima(Float.parseFloat(capacidadeMaxima));
            }
        }
        if(Float.parseFloat(capacidadeMinima)!=model.getCapacidadeMinima()){
            if(Float.parseFloat(capacidadeMinima)>model.getCapacidadeMaxima()){
                JOptionPane.showMessageDialog(view, "Capacidade Minima não pode ser maior que a Maxima");
            }else{
                model.setCapacidadeMinima(Float.parseFloat(capacidadeMinima));
            }
        }
        if(Float.parseFloat(vazao)!=model.getVazao()){
            model.setVazao(Float.parseFloat(vazao));
        }
        if(Float.parseFloat(precoCargueiro)!=model.getPrecoPorTipo().get("cargueiro")){
            model.removerPreco("cargueiro");
            model.setPrecoPorTipo("cargueiro",Float.parseFloat(precoCargueiro));
        }
        if(Float.parseFloat(precoPetroleiro)!=model.getPrecoPorTipo().get("petroleiro")){
            model.removerPreco("petroleiro");
            model.setPrecoPorTipo("petroleiro",Float.parseFloat(precoPetroleiro));
        }
        if(Float.parseFloat(precoTurismo)!=model.getPrecoPorTipo().get("turismo")){
            model.removerPreco("turismo");
            model.setPrecoPorTipo("turismo",Float.parseFloat(precoTurismo));
        }
        if(Float.parseFloat(precoBalsa)!=model.getPrecoPorTipo().get("balsa")){
            model.removerPreco("balsa");
            model.setPrecoPorTipo("balsa",Float.parseFloat(precoBalsa));
        }
        if(Float.parseFloat(precoBalsa)<0||Float.parseFloat(precoTurismo)<0||Float.parseFloat(precoPetroleiro)<0||Float.parseFloat(precoCargueiro)<0||Float.parseFloat(vazao)<0||Float.parseFloat(capacidadeMinima)<0||Float.parseFloat(capacidadeMaxima)<0||Float.parseFloat(largura)<0||Float.parseFloat(comprimento)<0){
            view.mostrarErro();
        }
    }
    public void abrir(){
        if (model.getStatus().equals("seca")||model.getStatus().equals("cheia")){
            model.setPorta(true);
            view.recado("Eclusa aberta");
            if(model.getOcupada()==false){
                 if(model.getFila().get(0).getSentido().equals("Subir")&& model.getStatus().equals("seca")){
                     view.sentido("RIO--->MAR");
                     model.getFila().get(0).entrar(model);
                     view.atualizaFila(model.tamanhoFila(),model.tempo());
                        if(model.tamanhoFila()==0){
                             view.sentidoProxima("Fila vazia");
                         }
                 }if(model.getFila().get(0).getSentido().equals("Descer")&& model.getStatus().equals("cheia")){
                     view.sentido("RIO<---MAR");
                     model.getFila().get(0).entrar(model);
                     view.atualizaFila(model.tamanhoFila(),model.tempo());
                     if(model.tamanhoFila()==0){
                             view.sentidoProxima("Fila vazia");
                      }
                 }
                 else{
                     if(model.getFila().get(0).getSentido().equals("Subir")){
                        view.sentidoProxima("RIO--->MAR");
                    }else{
                        view.sentidoProxima("RIO<---MAR");
                    }
                 }
            }else{
                if((model.getEmbarcacaoNaEclusa().getSentido().equals("Subir")&& model.getStatus().equals("cheia"))||(model.getEmbarcacaoNaEclusa().getSentido().equals("Descer")&& model.getStatus().equals("seca"))){
                    model.getEmbarcacaoNaEclusa().sair(model);
                    view.sentido(" ");
                }
                
  
            }
            view.atualizaFila(model.tamanhoFila(),model.tempo());
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
    public void mostrarEmbarcacao(){
        if(model.getEmbarcacaoNaEclusa()!=null){
            if(model.getEmbarcacaoNaEclusa()instanceof Balsa){
                Balsa balsa=(Balsa)model.getEmbarcacaoNaEclusa();
                tela.textoEmbarcacao(balsa.mensagemEmbarcacao());
                tela.nomeEmbarcacao("Balsa");
            }if(model.getEmbarcacaoNaEclusa()instanceof Cargueiro){
                Cargueiro carg=(Cargueiro)model.getEmbarcacaoNaEclusa();
                tela.textoEmbarcacao(carg.mensagemEmbarcacao());
                tela.nomeEmbarcacao("Navio Cargueiro");
            }if(model.getEmbarcacaoNaEclusa()instanceof NavioTuristico){
                NavioTuristico turi=(NavioTuristico)model.getEmbarcacaoNaEclusa();
                tela.textoEmbarcacao(turi.mensagemEmbarcacao());
                tela.nomeEmbarcacao("Navio Turistico");
            }if(model.getEmbarcacaoNaEclusa()instanceof Petroleiro){
                Petroleiro petro=(Petroleiro)model.getEmbarcacaoNaEclusa();
                tela.textoEmbarcacao(petro.mensagemEmbarcacao());
                tela.nomeEmbarcacao("Navio Petroleiro");
            }
            tela.setVisible(true);
        }
        
    }
    
    public void mostrarEmbarcacao(boolean remove){
        if(model.getUltimaNaFila()!=null){
            if(model.getUltimaNaFila()instanceof Balsa){
                Balsa balsa=(Balsa)model.getUltimaNaFila();
                removeView.textoEmbarcacao(balsa.mensagemEmbarcacao());
                removeView.nomeEmbarcacao("Balsa");
            }if(model.getUltimaNaFila()instanceof Cargueiro){
                Cargueiro carg=(Cargueiro)model.getUltimaNaFila();
                removeView.textoEmbarcacao(carg.mensagemEmbarcacao());
                removeView.nomeEmbarcacao("Navio Cargueiro");
            }if(model.getUltimaNaFila()instanceof NavioTuristico){
                NavioTuristico turi=(NavioTuristico)model.getUltimaNaFila();
                removeView.textoEmbarcacao(turi.mensagemEmbarcacao());
                removeView.nomeEmbarcacao("Navio Turistico");
            }if(model.getUltimaNaFila()instanceof Petroleiro){
                Petroleiro petro=(Petroleiro)model.getUltimaNaFila();
                removeView.textoEmbarcacao(petro.mensagemEmbarcacao());
                removeView.nomeEmbarcacao("Navio Petroleiro");
            }
        }
        
    }
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
}
