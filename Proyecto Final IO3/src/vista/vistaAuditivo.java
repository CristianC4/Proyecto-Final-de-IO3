/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vista;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author C4
 */
public class vistaAuditivo extends javax.swing.JFrame {
    
    int arregloNumeroImagenes[]=new int[12];//Arreglo que contendr� el numero correspondiente al nombre de las imagenes
    JLabel arregloLabelImagenes[]=new JLabel[12];//Arreglo con los componentes JLabel donde se pintan las imagenes
    int arregloControlRepeticiones[]=new int[6];//Arreglo que evitar� que un numero se repita mas de 2 veces
    int arregloControlParejas[]=new int[12];//Arreglo que permitir� llevar el control de las parejas encontradas
    int arregloCantClic[]=new int[12];//Arreglo que permitir� controlar los eventos producidos cada que se de clic en una imagen
	
    int clic=0;//Variable que determinar� cuantos clic se han dado antes de validar las parejas
    int vista1,vista2;//variables que indicar�n cuales son las imagenes que se han mostrado
    int puntaje=0;//Variable que permitir� llevar un conteo de los puntos 
    /** Creates new form vistaAuditivo */
       
    public vistaAuditivo() {
        initComponents();
        
       
    }
    private void inicializarArreglos() {
			
			inicializaArregloImagenes();
			
			//inicializa los arreglos en cero
			llenaArreglosConCeros(arregloControlRepeticiones);
			llenaArreglosConCeros(arregloNumeroImagenes);
			llenaArreglosConCeros(arregloControlParejas);
			llenaArreglosConCeros(arregloCantClic);
						
			 Random r=new Random();//permite la generacion de numeros aleatorios
			 int posicionImagen;
			  for (int i = 0; i < 12; i++) {
				
				 posicionImagen=r.nextInt(6);//la posicionImagen tendra un valor aleatorio entre 0 y 5, es decir, 6 valores posibles
	            /*Se valida que el numero aleatorio solo se repita 2 veces*/
	            if(arregloControlRepeticiones[posicionImagen]<2){
	                arregloNumeroImagenes[i]=posicionImagen+1;//almacena el valor que representa cada imagen (la imagen se llama con su numero), se suma 1 porq si el numero es 0 entonces queda en 1
	                System.out.print(arregloNumeroImagenes[i]+" , ");
	            	arregloControlRepeticiones[posicionImagen]++;//si se almacena el numero entonces se aumenta el valor para que sepamos que ya existe 1 vez
	            }else{
	                i--;//en caso de que el numero se repita entonces se devuelve una iteraci�n para que pueda continuar pidiendo numeros
	            }
			}
			 
		}
		
		 /**
		 *Metodo que permite inicializar el arreglo de JLabels con los objetos labels de las imagenes 
		 *y asignarle a cada uno la imagen inicial de incognita
		 */
		private void inicializaArregloImagenes() {
			arregloLabelImagenes[0]=etiImagen1;  arregloLabelImagenes[1]=etiImagen2;   arregloLabelImagenes[2]=etiImagen3;
			arregloLabelImagenes[3]=etiImagen4;  arregloLabelImagenes[4]=etiImagen5;   arregloLabelImagenes[5]=etiImagen6;
			arregloLabelImagenes[6]=etiImagen7;  arregloLabelImagenes[7]=etiImagen8;   arregloLabelImagenes[8]=etiImagen9;
			arregloLabelImagenes[9]=etiImagen10; arregloLabelImagenes[10]=etiImagen11; arregloLabelImagenes[11]=etiImagen12;
			
			for (int i = 0; i < 12; i++) {
				 arregloLabelImagenes[i].setIcon(new ImageIcon(getClass().getResource("/imagenes/quien.jpg")));
				 arregloLabelImagenes[i].addMouseListener((MouseListener) this);
			 }
		}

	    /**
	     * Inicializa los arreglos con ceros
	     * @param arreglo
	     */
	    private void llenaArreglosConCeros(int[] arreglo) {
			for (int i = 0; i < arreglo.length; i++) {
				arreglo[i]=0;
			}
		}


	/**
	 * Permite validar las parejas comparando las posiciones almacenadas al momento
	 * de mostrar las imagenes
	 * @param vista1
	 * @param vista2
	 * @param i
	 */
	private void validaParejas(int vista1, int vista2, int i) {
		/*valida si el valor en la posicion obtenida al encontrar la imagen 1 es igual al valor 
		 * en la posicion obtenida al encontrar la imagen 2*/
		if (arregloNumeroImagenes[vista1]==arregloNumeroImagenes[vista2]) {
			//si esto se cumple se suman 10 puntos y se asigna un 1 para determinar que en esas posiciones ya se encontraron parejas
				puntaje+=10;
				//lblPuntos.setText(puntaje+"");
				arregloControlParejas[vista1]=1;
				arregloControlParejas[vista2]=1;
                try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("build/classes/audio/acierto.wav").getAbsoluteFile());
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                         System.out.println("Error al reproducir el sonido."+ ex);
                        }
                                //Aqui se reproduce el sonido de yuju 
		}else{
			/*si no son iguales entonces se vuelve a poner la imagen de incognita y se reinicia 
			 * el valor de la posicion en el arreglo que cuenta cuantos clic se han dado en cada label*/ 
			arregloLabelImagenes[vista1].setIcon(new ImageIcon(getClass().getResource("/imagenes/quien.jpg")));
			arregloLabelImagenes[vista2].setIcon(new ImageIcon(getClass().getResource("/imagenes/quien.jpg")));
			arregloCantClic[vista1]=0;
			arregloCantClic[vista2]=0;
                        try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("build/classes/audio/error.wav").getAbsoluteFile());
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                         System.out.println("Error al reproducir el sonido."+ ex);
                        }
		}
	}


	
	
	/**
	 * El arregloControlParejas almacena 0 o 1, 0 indica que no hay parejas encontradas a�n para esa posicion, 1 indica que en esa posicion
	 * ya se encuentr� pareja, se habla de posiciones ya que en estas es donde se encuentran las imagenes.
	 * @param i
	 * @return
	 */
	private boolean verificaParejasEncontradas(int i) {
		
        return arregloControlParejas[i]!=1;
	}
	
	
	public void mouseExited(MouseEvent e) {
		/*Cuando se ejecuta este evento se permite realizar la validacion de parejas, solo cuando ya se han descubierto 2*/
		for (int i = 0; i < 12; i++) {
			if (e.getSource()==arregloLabelImagenes[i]) {
				if (clic==2) {
					validaParejas(vista1,vista2,i);
					clic=0;
				}				
			}
		}
	}
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        etiImagen3 = new javax.swing.JLabel();
        etiImagen1 = new javax.swing.JLabel();
        etiImagen2 = new javax.swing.JLabel();
        etiImagen4 = new javax.swing.JLabel();
        etiImagen5 = new javax.swing.JLabel();
        etiImagen6 = new javax.swing.JLabel();
        etiImagen7 = new javax.swing.JLabel();
        etiImagen8 = new javax.swing.JLabel();
        etiImagen9 = new javax.swing.JLabel();
        etiImagen10 = new javax.swing.JLabel();
        etiImagen11 = new javax.swing.JLabel();
        etiImagen12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        p1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        p2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        p3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        p4 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        p5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        p6 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        p7 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        p8 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        p9 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        p13 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        p10 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        p11 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        p12 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        p14 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        p15 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        p16 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        p17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aprendizaje Auditivo");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(320, 465));

        etiImagen3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen3.setText("jLabel2");
        etiImagen3.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen1.setText("jLabel2");
        etiImagen1.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen2.setText("jLabel2");
        etiImagen2.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen4.setText("jLabel2");
        etiImagen4.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen5.setText("jLabel2");
        etiImagen5.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen6.setText("jLabel2");
        etiImagen6.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen7.setText("jLabel2");
        etiImagen7.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen8.setText("jLabel2");
        etiImagen8.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen9.setText("jLabel2");
        etiImagen9.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen10.setText("jLabel2");
        etiImagen10.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen11.setText("jLabel2");
        etiImagen11.setPreferredSize(new java.awt.Dimension(100, 100));

        etiImagen12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quien.jpg"))); // NOI18N
        etiImagen12.setText("jLabel2");
        etiImagen12.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(etiImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiImagen3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(etiImagen4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiImagen5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiImagen6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(etiImagen7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiImagen8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiImagen9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(etiImagen10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiImagen11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiImagen12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiImagen3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiImagen4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiImagen6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiImagen5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiImagen7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiImagen9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiImagen8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiImagen10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiImagen12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiImagen11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Teoria Bayesiana de la decisión");

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel14.setText("La teoría");

        p1.setBackground(java.awt.Color.lightGray);
        p1.setForeground(java.awt.Color.blue);
        p1.setText("bayesiana");
        p1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setText("de la decisión es un conjunto de");

        p2.setBackground(java.awt.Color.lightGray);
        p2.setForeground(new java.awt.Color(0, 0, 255));
        p2.setText("alternativas");
        p2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel16.setText("que dan solución a un ");

        p3.setBackground(java.awt.Color.lightGray);
        p3.setForeground(java.awt.Color.blue);
        p3.setText("problema");
        p3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel17.setText("teniendo en cuenta o no sucesos que ya han sucedido ( a");

        p4.setBackground(java.awt.Color.lightGray);
        p4.setForeground(java.awt.Color.blue);
        p4.setText("priori");
        p4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setText("y a ");

        p5.setBackground(java.awt.Color.lightGray);
        p5.setForeground(java.awt.Color.blue);
        p5.setText("posteriori");
        p5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel19.setText(") , usando entonces la");

        p6.setBackground(java.awt.Color.lightGray);
        p6.setForeground(java.awt.Color.blue);
        p6.setText("teoría de bayes");
        p6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel20.setText("para escoger entre las posibles soluciones la mejor de ellas");

        p7.setBackground(java.awt.Color.lightGray);
        p7.setForeground(java.awt.Color.blue);
        p7.setText("criterios");
        p7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel22.setText("previamente establecidos: ");

        p8.setBackground(java.awt.Color.lightGray);
        p8.setForeground(java.awt.Color.blue);
        p8.setText("maximax");
        p8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel23.setText(": posibilidad optimista");

        p9.setBackground(java.awt.Color.lightGray);
        p9.setForeground(java.awt.Color.blue);
        p9.setText("hurwitz");
        p9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel24.setText(": Combina pesimista y optimista");

        jLabel25.setText("Savage ( ");

        jLabel26.setText(") : posibilidad optimista");

        p13.setBackground(java.awt.Color.lightGray);
        p13.setForeground(java.awt.Color.blue);
        p13.setText("laplace");
        p13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel27.setText(": Evalua distintas alternativas");

        p10.setBackground(java.awt.Color.lightGray);
        p10.setForeground(java.awt.Color.blue);
        p10.setText("minimax");
        p10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setText("Wald (");

        p11.setBackground(java.awt.Color.lightGray);
        p11.setForeground(java.awt.Color.blue);
        p11.setText("maximin");
        p11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel29.setText(") : minimiza la perdida");

        jLabel32.setText("A posteriori : Estimaciones de las");

        p12.setBackground(java.awt.Color.lightGray);
        p12.setForeground(java.awt.Color.blue);
        p12.setText("probabilidades");
        p12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel30.setText("con respecto a otro asociado");

        jLabel31.setText("Dentro de sus posibles aplicaciones encontramos : ");

        jLabel34.setText("Redes");

        p14.setBackground(java.awt.Color.lightGray);
        p14.setForeground(java.awt.Color.blue);
        p14.setText("neuronales");
        p14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel36.setText("Mecánica");

        p15.setBackground(java.awt.Color.lightGray);
        p15.setForeground(java.awt.Color.blue);
        p15.setText("cuantica");
        p15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel38.setText("Análisis de");

        p16.setBackground(java.awt.Color.lightGray);
        p16.setForeground(java.awt.Color.blue);
        p16.setText("costos");
        p16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel40.setText("Decisiones");

        p17.setBackground(java.awt.Color.lightGray);
        p17.setForeground(java.awt.Color.blue);
        p17.setText("medicas");
        p17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel21.setText(", y además usando unos");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(p3)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(p1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p5))))
                    .addComponent(jLabel31)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(p8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(p13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27)))
                        .addGap(114, 114, 114)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel29))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(p9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel24))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(398, 398, 398)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p15))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p17))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p14))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p16))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(p1)
                    .addComponent(jLabel15)
                    .addComponent(p2)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p3)
                    .addComponent(jLabel17)
                    .addComponent(p4)
                    .addComponent(jLabel18)
                    .addComponent(p5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(p6)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(p7)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p8)
                    .addComponent(jLabel23)
                    .addComponent(p9)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(p10)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(p11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p13)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(p12)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(p14)
                    .addComponent(jLabel36)
                    .addComponent(p15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel40)
                        .addComponent(p17))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38)
                        .addComponent(p16)))
                .addContainerGap(153, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1071, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(372, 372, 372)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(192, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel etiImagen1;
    private javax.swing.JLabel etiImagen10;
    private javax.swing.JLabel etiImagen11;
    private javax.swing.JLabel etiImagen12;
    private javax.swing.JLabel etiImagen2;
    private javax.swing.JLabel etiImagen3;
    private javax.swing.JLabel etiImagen4;
    private javax.swing.JLabel etiImagen5;
    private javax.swing.JLabel etiImagen6;
    private javax.swing.JLabel etiImagen7;
    private javax.swing.JLabel etiImagen8;
    private javax.swing.JLabel etiImagen9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel p1;
    private javax.swing.JLabel p10;
    private javax.swing.JLabel p11;
    private javax.swing.JLabel p12;
    private javax.swing.JLabel p13;
    private javax.swing.JLabel p14;
    private javax.swing.JLabel p15;
    private javax.swing.JLabel p16;
    private javax.swing.JLabel p17;
    private javax.swing.JLabel p2;
    private javax.swing.JLabel p3;
    private javax.swing.JLabel p4;
    private javax.swing.JLabel p5;
    private javax.swing.JLabel p6;
    private javax.swing.JLabel p7;
    private javax.swing.JLabel p8;
    private javax.swing.JLabel p9;
    // End of variables declaration//GEN-END:variables

}
