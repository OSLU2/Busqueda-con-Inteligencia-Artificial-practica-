/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castillo2;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author oswal
 */
    class ManejadorVentana implements WindowListener{//clase interna
        @Override
        public void windowActivated(WindowEvent e) {		
        }
        @Override
        public void windowClosed(WindowEvent e) {			
        }
        @Override
        public void windowClosing(WindowEvent e) {
                System.exit(0);
        }
        @Override
        public void windowDeactivated(WindowEvent e) {		
        }
        @Override
        public void windowDeiconified(WindowEvent e) {	
        }
        @Override
        public void windowIconified(WindowEvent e) {			
        }
        @Override
        public void windowOpened(WindowEvent e) {		
        }
		
	}