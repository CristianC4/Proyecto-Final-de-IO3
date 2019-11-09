/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javax.swing.JOptionPane;
import vista.vistaEncuestaPNL;

/**
 *
 * @author C4
 */
public class controlEncuestaPNL {
    
    private vistaEncuestaPNL vistaEncuesta;

    public controlEncuestaPNL() {
        vistaEncuesta = new vistaEncuestaPNL(this);
        vistaEncuesta.setVisible(true);
    }   
    
    public String definirAprendizaje(){
        String aprendizaje = "";
        System.out.println(vistaEncuesta.getRespuestas());
        if(vistaEncuesta.getRespuestas() == null){
            JOptionPane.showConfirmDialog(null, "Ha ocurrido un error", "Error",JOptionPane.ERROR_MESSAGE);
        }
        int visual = 0, kinestesico = 0, auditivo = 0;
        for (int i = 0; i < 40; i++) {
            if(vistaEncuesta.getRespuestas().get(i).equals(0)){
                visual++;
            }else if(vistaEncuesta.getRespuestas().get(i).equals(1)){
                auditivo++;
            }
            else if(vistaEncuesta.getRespuestas().get(i).equals(2)){
                kinestesico++;
            }
        }
        System.out.println(visual+" ; "+auditivo+" ; "+kinestesico);
        if (auditivo >= kinestesico && auditivo >= visual){
            aprendizaje = "auditivo";
        }
        if (visual >= kinestesico && visual >= auditivo){
            aprendizaje = "visual";
        }
        if(kinestesico >= auditivo && kinestesico >= visual) {
            aprendizaje = "kinestesico";
        }
        return aprendizaje;
    }
    
}
