package com.mycompany.formulario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class Formulario extends JFrame implements ActionListener{
    
    JLabel lNome, lTelefone,lSexo,lCidade,lCurso;
    JTextField tfNome, tfTelefone,tfCidade;
    JButton bSalvar, bDeletar, bLimpar, bSair;
    JRadioButton rbMasculino, rbFeminino;
    ButtonGroup buttonGroup;
    
    String [] curso = {"", "Informática Básica", "Wordpress Básico", "Pacote Office" };
    JComboBox cbCurso;

    JTable tabela;
    DefaultTableModel dtModel;
    JScrollPane barraRolagem;
    
    
    public Formulario() {
        setLayout(new FlowLayout());
        setSize(770, 600);
        setTitle("EF INFORMÁTICA");
        setLocationRelativeTo(null); //Centraliza a janela no meio da tela
        setVisible(true);

        lNome = new JLabel("Nome:");
        add(lNome);
        tfNome = new JTextField(14);
        tfNome.setDocument(new LimitaCaracteres(60,LimitaCaracteres.TipoEntada.STRING));
        add(tfNome);

        lTelefone = new JLabel("Telefone:");
        add(lTelefone);
        tfTelefone = new JTextField(10);
        tfTelefone.setDocument(new LimitaCaracteres(20,LimitaCaracteres.TipoEntada.NUMERO));
        add(tfTelefone);

        lCidade = new JLabel("Cidade:");
        add(lCidade);
        tfCidade = new JTextField(13);
        tfCidade.setDocument(new LimitaCaracteres(60,LimitaCaracteres.TipoEntada.STRING));
        add(tfCidade);

        lSexo = new JLabel("Sexo:");
        add(lSexo);

        rbMasculino = new JRadioButton("Masculino");
        add(rbMasculino);
        rbFeminino = new JRadioButton("Feminino");
        add(rbFeminino);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(rbMasculino);
        buttonGroup.add(rbFeminino);


        lCurso = new JLabel("Curso:");
        add(lCurso);
        cbCurso = new JComboBox(curso);
        add(cbCurso);

        bSalvar = new JButton("Salvar");
        bSalvar.addActionListener(this);
        add(bSalvar);
        
        bDeletar = new JButton("Deletar");
        bDeletar.addActionListener(this);
        add(bDeletar);
        
        bLimpar = new JButton("Limpar");
        bLimpar.addActionListener(this);
        add(bLimpar);

        bSair = new JButton("Sair");
        bSair.addActionListener(this);                                                  
        add(bSair);

        //TABELA
        dtModel = new DefaultTableModel();
        dtModel.addColumn("NOME");
        dtModel.addColumn("TELEFONE");
        dtModel.addColumn("CIDADE");
        dtModel.addColumn("SEXO");
        dtModel.addColumn("CURSO");
           
        tabela = new JTable(dtModel){
            @Override
            public boolean isCellEditable(int rowIndex, int vColIndex) { // Para que a células não sejam editaveis.
            return false;
        }
        }; 
        tabela.setPreferredScrollableViewportSize(new Dimension(700, 400)); // Tamanho da tabela
        
        barraRolagem = new JScrollPane(tabela);
        add(barraRolagem);
        
    }

    public static void main(String[] args) {
        Formulario f = new Formulario();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        boolean tfPreenchidos = tfNome.getText().length() > 0 && tfTelefone.getText().length() > 0 && tfCidade.getText().length() > 0;
        boolean rbSelecionados = rbMasculino.isSelected() || rbFeminino.isSelected();
        boolean cbSelecionado = cbCurso.getSelectedIndex() > 0;
        boolean camposPrenchidos =  tfPreenchidos && rbSelecionados && cbSelecionado;
        
        
        if(ae.getSource() == bSalvar){
            if(camposPrenchidos){
                String sexoSelecionado = "";
                if(rbMasculino.isSelected()){
                    sexoSelecionado = "Masculino";
                }else if(rbFeminino.isSelected()){
                    sexoSelecionado = "Feminino";
                }

                String cursoSelecionado = curso[cbCurso.getSelectedIndex()];
                Object[] dados = {tfNome.getText(),tfTelefone.getText(),tfCidade.getText(),sexoSelecionado ,cursoSelecionado};
                dtModel.addRow(dados);

                limparCampos();
            }else{
                JOptionPane.showMessageDialog(null, 
                    "Preencha todos os campos.", 
                    "Campo Vazio", 
                    JOptionPane.INFORMATION_MESSAGE); 
            
            }
        }
        
        if(ae.getSource() == bDeletar){
            int linha = tabela.getSelectedRow();
            if(linha != -1){
                dtModel.removeRow(linha);
            }else{
                JOptionPane.showMessageDialog(null, 
                    "Selecione um item para deletar.", 
                    "Nenhum Item selecionado", 
                    JOptionPane.INFORMATION_MESSAGE); 
            }
            
        }
        
        if (ae.getSource() == bLimpar) {
            limparCampos();
        }

        if (ae.getSource() == bSair) {
            System.exit(0);
        }        
    }
    
    public void limparCampos(){
        tfNome.setText("");
        tfTelefone.setText("");
        tfCidade.setText("");

        tfNome.requestFocus();
        cbCurso.setSelectedIndex(0);
        buttonGroup.clearSelection();
    }
}