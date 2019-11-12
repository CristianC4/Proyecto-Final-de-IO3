/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.util.Random;
import javax.swing.JLabel;

/**
 *
 * @author C4
 */




public class vistaVisual extends javax.swing.JFrame {

    /**
     * Creates new form vistaVisual
     */
        Final fin;
        JLabel letra[][];
        JLabel palabra[];
        String palabras[];//al macenas las palabras en un arreglo de string
        int iniciox[];
        int inicioy[];
        boolean gano; 
        boolean direccion[];
    public vistaVisual() {
        initComponents();
        palabra = new JLabel[]{p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17};
        this.setLocationRelativeTo(null);
        cargar();
        palabras=new String[17];
        palabra=new JLabel[]{p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17};//
        for (int i = 0; i < letra.length; i++) {
            palabras[i]=palabra[i].getText();//pasa la palabra del arreglo label al al arreglo de string
        }
    }
     public void cargar()
    {
        gano=false;
        iniciox=new int[20];//crea un arreglo de enteros para guadar las posiciones de las palabras en x
        inicioy=new int[17];//crea un arreglo de enteros para guadar las posiciones de las palabras en y
        direccion=new boolean[20];//crea un arreglo de enteros para guadar las direcion de las palabras ya sea hacia alante o hacia tras
        celdasdeletras();
        colocarpalabras();
        llenarespaciosvacios();
    }
   public void celdasdeletras() 
    {
        letra=new JLabel[17][20];//crea la matriz de celdas donde va cada letra
        for (int i = 0; i < 17; i++) 
        {
            for (int j = 0; j < 20; j++) {
                letra[i][j]=new JLabel("", javax.swing.SwingConstants.CENTER);//crea la casilla la vacia y con una alineacion centrada
                letra[i][j].setName("");//le pone un nombre a la casilla en este caso no le pone ninguno
                letra[i][j].setBackground(Color.WHITE);//coloca la casilla de color blanco
                letra[i][j].setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // asigna el tipo y el tamaño de la letra
                letra[i][j].setForeground(new java.awt.Color(0, 5, 2));
                letra[i][j].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                letra[i][j].setOpaque(true);//esto es para que se pueda ver el color de la casilla o cajonsito donde va la letra
                letra[i][j].setBorder(new javax.swing.border.LineBorder(Color.BLACK, 1));//pone a la casiilla en borde con una linea negra
                letra[i][j].setText(""+i+","+j+"");
                letra[i][j].addMouseListener(new java.awt.event.MouseAdapter() {//pone a la casilla a la escucha del mouse para saber cuando se esta dando clic
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        presioneClick(evt);//llama al metodo que debe ejecutarse cuando se da clic
                    }
                });
                Sopa_de_letra.add(letra[i][j]);//coloca la pasilla en el panel Sopa_de_letra
            }
        }
    }
   public void presioneClick(java.awt.event.MouseEvent evt) {
        if (!gano) {//verifica si gano el juego
            if (evt.getComponent().getBackground().equals(Color.WHITE))//verifica si la casilla esta de color blanco 
            {
                evt.getComponent().setBackground(new java.awt.Color(51, 51, 255));//si esta de color blanco la pone de color azul
                tachar();
            }else if(evt.getComponent().getName().equals(""))//pregunta si la casilla no tiene una letra de alguna palabra
            {
                evt.getComponent().setBackground(Color.WHITE);//pone la casilla de color blanco
            }
        }
    }
   public void tachar() 
    {
        for (int i = 0; i < 17; i++) 
        {
            if (!palabra[i].getText().substring(0, 1).equals("<")) {
                if (tacharLetra(iniciox[i],inicioy[i],palabra[i].getText().length(),direccion[i]))//pregunta si hay una palabra encontrada
                {
                    palabra[i].setText("<html><body><s>"+palabra[i].getText()+"</s></body></html>");//tacha la palabra
                    break;
                }
            }
        }
        boolean aux=true;//ayuda para saber si ya todas las palabras estan tachadas
        for (int i = 0; i < letra.length; i++)
        {
            if (!palabra[i].getText().substring(0, 1).equals("<")) 
            {
                aux=false;
                break;
            }
        }
        if (aux) {
            if (!(fin instanceof Final)) 
            { //esto comprueba si la ventana no esta en memoria, entonces la instancia
                fin = new Final();   
                gano=true;
            } 
            
        }
    }
    public boolean  tacharLetra(int x,int y,int tamaño,boolean direccion) {
        boolean respuesta=true;
        if (direccion) {
            for (int i = y; i < tamaño+y; i++) {
                if (letra[x][i].getBackground().equals(Color.WHITE)) {
                    respuesta=false;
                    break;
                }
            }
        }else
        {
            for (int j = y; j >y-tamaño; j--) {
                if (letra[x][j].getBackground().equals(Color.WHITE)) {
                    respuesta=false;
                    break;
                }
            }
        }
        return respuesta;
    }
    
   public void colocarpalabras() 
    {/*
        String palabra[]={p1.getText(),p2.getText(),p3.getText(),p4.getText(),p5.getText(),p6.getText(),p7.getText(),p8.getText(),p9.getText(),p10.getText(),p11.getText(),p12.getText(),p13.getText(),p14.getText(),p15.getText(),p16.getText(),p17.getText()};
        Random random=new Random();//estemetodo ayuda a crear numeros aleatorios
        int iniciax=0;//posicion x donde inicia la palabra
        int iniciay;//posicion y donde inicia la palabra
        int unico[]=NumerosSinRepeticiones(17);//evita que en una fila se generen mas de una vez
        iniciox=unico;
        for (int i = 0; i < palabra.length; i++) {
            if (palabra[i].length()<20) {
                iniciax=unico[i];
                iniciay=(int)(random.nextDouble()*17-1);
                int extrae=0;//ayuda para extraer las letras de la palabra
                if (iniciay+palabra[i].length()<20) {
                    for (int j = iniciay; j < iniciay+palabra[i].length(); j++) {
                        letra[iniciax][j].setText(palabra[i].substring(extrae, extrae+1));//extrae una letra de la palabra
                        letra[iniciax][j].setName("1");//pone el nombre a la casilla para que se sepa que hay va una letra de una palabra
                        extrae++;//esto es para que se extraiga la siguiente letra de la palabra
                        inicioy[i]=iniciay;
                        direccion[i]=true;
                    }
                } else if (iniciay-palabra[i].length()>0)
                {
                    for (int j = iniciay; j >iniciay-palabra[i].length() ; j--) {
                        letra[iniciax][j].setText(palabra[i].substring(extrae, extrae+1));
                        letra[iniciax][j].setName("1");
                        extrae++;
                        inicioy[i]=iniciay;
                        direccion[i]=false;
                    }
                }
            }
        }*/
    }
   public int[] NumerosSinRepeticiones(int repeticiones) {
        int numeros[]=new int[repeticiones];
        for (int i = 0; i < repeticiones; i++) {
            numeros[i]=-1;
        }
        Random random=new Random();
        boolean aux ;//informa si la fila esta o no repetida
        int numero=0;
        for (int x = 0; x < repeticiones; x++) 
        {
            aux = true;
            while (aux) {  
                aux=false;
                numero=(int)(random.nextDouble()*18-1);
                for (int j = 0; j < numeros.length; j++) {
                    if (numeros[j]==numero) {
                        aux=true;
                        break;
                    }
                }
            }
            numeros[x]=numero;
        }
        return numeros;
    }
   public void llenarespaciosvacios() 
    {/*
        //este arreglo ayuda a poner las letras del abecedario
        String abc[]={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        Random random=new Random();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 20; j++) {
                if (letra[i][j].getText().equals("")) {//si la casilla esta vacia pongale una letra del arreglo abc
                    letra[i][j].setText(abc[(int)(random.nextDouble()*abc.length-1)]);//aqui pone la letra
                }
            }
        }*/
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Sopa_de_letra = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        p1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        p2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        p3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        p4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        p5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        p6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        p7 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        p8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        p9 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        p13 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        p10 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        p11 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        p12 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        p14 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        p15 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        p16 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        p17 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aprendizaje Visual");
        setBackground(new java.awt.Color(17, 254, 239));

        Sopa_de_letra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Sopa_de_letra.setLayout(new java.awt.GridLayout(15, 20));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Teoría Bayesiana de la decisión");

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("La teoría");

        p1.setBackground(java.awt.Color.lightGray);
        p1.setForeground(java.awt.Color.blue);
        p1.setText("bayesiana");
        p1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setText("de la decisión es un conjunto de");

        p2.setBackground(java.awt.Color.lightGray);
        p2.setForeground(new java.awt.Color(0, 0, 255));
        p2.setText("alternativas");
        p2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("que dan solución a un ");

        p3.setBackground(java.awt.Color.lightGray);
        p3.setForeground(java.awt.Color.blue);
        p3.setText("problema");
        p3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setText("eniendo en cuenta o no sucesos que ya han sucedido ( a");

        p4.setBackground(java.awt.Color.lightGray);
        p4.setForeground(java.awt.Color.blue);
        p4.setText("priori");
        p4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setText("y a ");

        p5.setBackground(java.awt.Color.lightGray);
        p5.setForeground(java.awt.Color.blue);
        p5.setText("posteriori");
        p5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setText(") , usando entonces la");

        p6.setBackground(java.awt.Color.lightGray);
        p6.setForeground(java.awt.Color.blue);
        p6.setText("teoría de bayes");
        p6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("para escoger entre las posibles soluciones la mejor de ellas");

        jLabel16.setText(", y además usando unos");

        p7.setBackground(java.awt.Color.lightGray);
        p7.setForeground(java.awt.Color.blue);
        p7.setText("criterios");
        p7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setText("previamente establecidos: ");

        p8.setBackground(java.awt.Color.lightGray);
        p8.setForeground(java.awt.Color.blue);
        p8.setText("maximax");
        p8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel19.setText(": posibilidad optimista");

        p9.setBackground(java.awt.Color.lightGray);
        p9.setForeground(java.awt.Color.blue);
        p9.setText("hurwitz");
        p9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel21.setText(": Combina pesimista y optimista");

        jLabel22.setText("Savage ( ");

        jLabel23.setText(") : posibilidad optimista");

        p13.setBackground(java.awt.Color.lightGray);
        p13.setForeground(java.awt.Color.blue);
        p13.setText("laplace");
        p13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel27.setText(": Evalua distintas alternativas");

        p10.setBackground(java.awt.Color.lightGray);
        p10.setForeground(java.awt.Color.blue);
        p10.setText("minimax");
        p10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel24.setText("Wald (");

        p11.setBackground(java.awt.Color.lightGray);
        p11.setForeground(java.awt.Color.blue);
        p11.setText("maximin");
        p11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setText(") : minimiza la perdida");

        jLabel32.setText("A posteriori : Estimaciones de las");

        p12.setBackground(java.awt.Color.lightGray);
        p12.setForeground(java.awt.Color.blue);
        p12.setText("probabilidades");
        p12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setText("con respecto a otro asociado");

        jLabel29.setText("Dentro de sus posibles aplicaciones encontramos : ");

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(p4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(p5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(p1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel29))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p14))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p16))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(p7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(p8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel19))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(p10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel23))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(p13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel27)))
                                .addGap(114, 114, 114)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(p11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel25))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(p9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel21))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(p12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel28))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(p15))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel40)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(p17)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(p1)
                    .addComponent(jLabel5)
                    .addComponent(p2)
                    .addComponent(jLabel7)
                    .addComponent(p3)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(p5)
                        .addComponent(jLabel13)
                        .addComponent(p6)
                        .addComponent(jLabel2)
                        .addComponent(jLabel16))
                    .addComponent(p4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p7)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p8)
                    .addComponent(jLabel19)
                    .addComponent(p9)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(p10)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(p11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p13)
                    .addComponent(jLabel27)
                    .addComponent(jLabel32)
                    .addComponent(p12)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                        .addComponent(p16))))
        );

        jMenuBar1.setBackground(new java.awt.Color(2, 19, 25));

        jMenu1.setForeground(new java.awt.Color(254, 254, 254));
        jMenu1.setText("Reiniciar");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Sopa_de_letra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Sopa_de_letra, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
 //esta reinicia el juego
        for (int i = 0; i < letra.length; i++) {
            palabra[i].setText(palabras[i]);//asigna a los label de la derecha las palabras
        }
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 20; j++) {
                Sopa_de_letra.remove(letra[i][j]);//quita el panel  Sopa_de_letra
            }
        }
        cargar();//carga el juego
    }//GEN-LAST:event_jMenu1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Sopa_de_letra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
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
