
package Castillo2;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author oswal
 */
public class VentanaPrincipal extends  javax.swing.JFrame implements interfaTerreno {
    private final int LARGO_VENTANA=700;
    private final int ANCHO_VENTANA=700;
    private static final String NOMBRE_APLICACION="New Londo";
    public static final long serialVersionUID=1;
    private final String POSICION_PANEL_BOTONES="South";
    private static final int SLIDER_MIN = 20;//jslider
    private static final int SLIDER_MAX = 80;
    private static final int SLIDER_INICIO=20;
    private JButton botonPerso;
    private JButton botonSol;
    private JButton botonReinicio;
    private Panel panelbotones;
    public final int MAXIMO_FILAS=15;
    public final int MAXIMO_COLUM=15;
    private String piso="/ImgAnorLondo/piso.jpg";//50x50
    private String perso="/ImgAnorLondo/solaris.jpg";
    private String meta="/ImgAnorLondo/sol.jpg";
    private String obsta="/ImgAnorLondo/obsta1.jpg" ;
    private boolean reinicio,pon;
    JLabel [][] MatrizLabelses;
    public Panel panelLabel;
    JSlider PorcenObsta;
    private int porcentaje=0;
    objetoTerreno personaje,objetivo;
    objetoTerreno obstaculos[];
    private int contaObstaPuestos=0;
    //Terreno terreno;
    public VentanaPrincipal()
    {
        super(NOMBRE_APLICACION);  
        iniciaLabels();  
        iniciarComponentesBotones();      
        setSize(LARGO_VENTANA,ANCHO_VENTANA);
        addWindowListener(new ManejadorVentana());
    }
    private void iniciarComponentesBotones()
    {
        personaje =new objetoTerreno();
        objetivo =new objetoTerreno();
        PorcenObsta= new JSlider(SLIDER_MIN,SLIDER_MAX,SLIDER_INICIO);
        
        
        botonPerso=new JButton("Solaris");
        botonSol=new JButton("Sol");
        botonReinicio=new JButton("Reiniciar");
        panelbotones=new Panel();
        add(POSICION_PANEL_BOTONES,panelbotones);        
        panelbotones.setLayout(new FlowLayout());
        panelbotones.add(botonPerso);
        panelbotones.add(botonSol);
        panelbotones.add(botonReinicio);
        panelbotones.add(PorcenObsta);
        PorcenObsta.addChangeListener(new PonPorcenObs());
        botonSol.addActionListener(new ManejaBotoMeta());
        botonPerso.addActionListener(new ManejaBotoPerso());
        botonReinicio.addActionListener(new Reinicio());
    }
    private void iniciaLabels(){
        MatrizLabelses = new JLabel[MAXIMO_FILAS][MAXIMO_COLUM];
        panelLabel= new Panel();
        panelLabel.setBackground(Color.gray);
        panelLabel.setLayout(new GridLayout(MAXIMO_FILAS, MAXIMO_COLUM));      
        InsertarPiso();
        add(panelLabel);
    }
    void InsertarPiso(){
        
        for (int i = 0; i < MAXIMO_FILAS; i++) {
            for (int j = 0; j < MAXIMO_COLUM; j++) {
                JLabel nuevo =new JLabel();
                nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource(piso)));
                nuevo.setBorder(new LineBorder(Color.white));
                MatrizLabelses[i][j]=nuevo;
                panelLabel.add(MatrizLabelses[i][j]);
                RedibujaPanel();
            }
        }
    }
    private void RedibujaPanel(){
        panelLabel.validate();
        panelLabel.repaint();
    }
    private void modificaMapa(String aux){
        if(pon){
        for (int i = 0; i < MAXIMO_FILAS; i++) {
            for (int j = 0; j < MAXIMO_COLUM; j++) {
                if(!existe(i, j)){
                    int x,y;
                    x=i;
                    y=j;
                    MatrizLabelses[i][j].addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent me) {    
                            MatrizLabelses[x][y].setIcon(new javax.swing.ImageIcon(getClass().getResource(aux)));
                            ocupar(aux,x,y);
                        }
                        @Override
                        public void mousePressed(MouseEvent me) {}
                        @Override
                        public void mouseReleased(MouseEvent me) {}
                        @Override
                        public void mouseEntered(MouseEvent me) {}
                        @Override
                        public void mouseExited(MouseEvent me) {}
                    }); 
                }
                }
                
            }
        }
        pon=false;
        if(reinicio){
            for (int i = 0; i < MAXIMO_FILAS; i++) {
                for (int j = 0; j < MAXIMO_COLUM; j++) {
                    MatrizLabelses[i][j].setIcon(new javax.swing.ImageIcon(getClass().getResource(aux)));
                    
                }
            }
        }
         
    }

    @Override
    public void imprimir(String a) {
         
    }
    class ManejaBotoPerso implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            Object control=ae.getSource();
            if(control.equals(botonPerso)){
                if(!personaje.isExiste()){
                    pon=true;
                    modificaMapa(perso);
                    
                }   
                else{
                    pon=false;
                }
            }
        }    
    }
    class ManejaBotoMeta implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            Object control=ae.getSource();
            if(control.equals(botonSol)){
                if(!objetivo.isExiste()){
                    pon=true;
                    modificaMapa(meta);
                }
                else{
                    pon=false;
                }
            }
        }
    }
    class Reinicio implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource()==botonReinicio){
                pon=false;
                objetivo.setExiste(false);
                personaje.setExiste(false);
                for(int i=0;i<porcentaje;i++){
                    obstaculos[i].setExiste(false);
                }
                contaObstaPuestos=0;
                reinicio=true;
                modificaMapa(piso);
            }
        }
    }
    class PonPorcenObs implements ChangeListener{
        @Override//agregar al mapa
        public void stateChanged(ChangeEvent ce) {
             JSlider slider= (JSlider)ce.getSource();
             if(!slider.getValueIsAdjusting()){
                 int porcen =(int)slider.getValue();
                 porcentaje=((porcen*225)/100);
                 obstaculos =new objetoTerreno[porcentaje];
                 System.out.println("porcentaje elegido:"+porcen);
                 System.out.println("cantidad total:"+porcentaje);
                 for(int i=0;i<porcentaje;i++){
                     objetoTerreno obstaculo= new objetoTerreno();
                     obstaculos[i]=obstaculo;
                 }
                 InsertarObstaculos(); 
             }
             
        }
    }
    void InsertarObstaculos(){
        int randomX;
        int randomY;
        int aux;
        String randomImg;
        for (int i = 0; i < porcentaje; i++) {
            if(contaObstaPuestos<porcentaje ){
                randomX=(int)(Math.random()*MAXIMO_FILAS);
                randomY=(int)(Math.random()*MAXIMO_COLUM);
                aux=(int)(Math.random()*10)+1;
                randomImg="/ImgAnorLondo/obsta"+ aux+".jpg";
                MatrizLabelses[randomX][randomY].setIcon(new javax.swing.ImageIcon(getClass().getResource(randomImg)));
                ocupar(obsta,randomX,randomY);
            }
        }
    }
    void ocupar(String a,int fil,int colm){
        if(a.equals(perso)){
            personaje.setExiste(true);
            personaje.setFila(fil);
            personaje.setColumna(colm);
        }
        if(a.equals(meta)){
            objetivo.setFila(fil);
            objetivo.setColumna(colm);
            objetivo.setExiste(true);
        }
        if(a.equals(obsta)){
            obstaculos[contaObstaPuestos].setFila(fil);
            obstaculos[contaObstaPuestos].setColumna(colm);
            obstaculos[contaObstaPuestos].setExiste(true);
            contaObstaPuestos++;
        }
    }
    boolean existe(int fil,int colum){
        if( personaje.getFila() == fil && personaje.getColumna()== colum
                && personaje.isExiste()){
            return personaje.isExiste();
        }
        if(objetivo.getFila()== fil && objetivo.getColumna()==colum
                && objetivo.isExiste()){
            return  objetivo.isExiste();
        }
        for(int i=0;i<contaObstaPuestos;i++){
            if(obstaculos[i].getFila()==fil && obstaculos[i].getColumna()==colum
                    && obstaculos[i].isExiste()){
                return obstaculos[i].isExiste();
            }
        }
        return false;
    }
}
