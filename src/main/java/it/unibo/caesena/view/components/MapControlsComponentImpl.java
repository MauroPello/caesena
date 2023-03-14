package it.unibo.caesena.view.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MapControlsComponentImpl extends JPanel implements MapControlsComponent<JPanel>{
    
    private JPanel currentMapControlsComponent = new JPanel();

    JButton zoomIn = new JButton("Zoom +");
    JButton zoomOut = new JButton("Zoom -");
    private JPanel ArrowsPanel = new JPanel();
    JButton UpRow = new JButton("UP");
    JButton DownRow = new JButton("DOWN");
    JButton LeftRow = new JButton("LEFT");
    JButton RightRow = new JButton("RIGHT");
    JButton nextStep = new JButton("");
    JButton EndTurn = new JButton("");

    public MapControlsComponentImpl() {
        currentMapControlsComponent.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ArrowsPanel.setLayout(new BorderLayout());

        ArrowsPanel.add(UpRow);
        ArrowsPanel.add(LeftRow);
        ArrowsPanel.add(RightRow);
        ArrowsPanel.add(DownRow);

        UpRow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }

        });

        LeftRow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }

        });

        RightRow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }

        });

        DownRow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }

        });

        currentMapControlsComponent.add(zoomIn);
        currentMapControlsComponent.add(zoomOut);
        currentMapControlsComponent.add(ArrowsPanel);
        currentMapControlsComponent.add(nextStep);
        currentMapControlsComponent.add(EndTurn);
    }

    
    
    
    private void ciao() {

    }
    /*
     * pulsante zoom
     * pulsante dezoom
     * 
     * pulsante conferma
     * pulsante fine turno
     */
}
