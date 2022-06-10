package com.mycompany.formulario;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitaCaracteres extends PlainDocument {
    
    public enum TipoEntada{NUMERO,STRING};
    
    private int qtdCaracteres;
    private TipoEntada tpEntrada;

    public LimitaCaracteres(int qtdCaracteres, TipoEntada tpEntrada) {
        this.qtdCaracteres = qtdCaracteres;
        this.tpEntrada = tpEntrada;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet as) throws BadLocationException {
        if(str == null || getLength() == qtdCaracteres){
            return;
        }
        int totalCarac = getLength() + str.length();
        
        //filtro de caracteres
        String regex = "";
        switch(tpEntrada){
            case NUMERO: 
                regex = "[^0-9()\\-]";
                break;
            case STRING:
                regex = "[^\\p{IsLatin} ]";
                break;
        }
        //substituição
        str = str.replaceAll(regex, "");
        
        if(totalCarac <= qtdCaracteres){
            super.insertString(offs, str, as); 
        }else{
           String nova = str.substring(0,qtdCaracteres);
           super.insertString(offs, nova, as);
        }
        
        
    }
    
    
    
}
