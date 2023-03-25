package it.unibo.caesena.view.components;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.multi.MultiButtonUI;

import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.ImageIconUtil;

public class SectionSelectorComponentImpl extends JPanel implements SectionSelectorComponent {
    private final Tile currentTile;
    private Optional<TileSection> currentSectionSelected = Optional.empty();

    public SectionSelectorComponentImpl(Tile tile, Dimension dimension) {
        super();
        this.currentTile = tile;
        this.setSize(dimension);
        redraw();
        this.setVisible(true);
    }

    @Override
    public TileSection getSelectedSection() {
        return currentSectionSelected.orElseThrow(()->new IllegalStateException("Tried to get section but none was selected"));
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Image image = ImageIconUtil.getTileImage(this.currentTile);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    private void redraw() {
        this.removeAll();
        this.drawSections();
        this.validate();
        this.repaint();
    }

    private void drawSections() {
        JPanel container = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return getTotalSize();
            }
        };
        container.setLayout(new GridBagLayout());
        container.setSize(this.getSize());
        this.add(container);

        SectionButton upLeftButton = new SectionButton(TileSection.UpLeft);
        SectionButton upCenterButton = new SectionButton(TileSection.UpCenter);
        SectionButton upRightButton = new SectionButton(TileSection.UpRight);

        SectionButton leftUpButton = new SectionButton(TileSection.LeftUp);
        SectionButton leftCenterButton = new SectionButton(TileSection.LeftCenter);
        SectionButton leftDownButton = new SectionButton(TileSection.LeftDown);

        SectionButton rightUpButton = new SectionButton(TileSection.RightUp);
        SectionButton rightCenterButton = new SectionButton(TileSection.RightCenter);
        SectionButton rightDownButton = new SectionButton(TileSection.RightDown);


        SectionButton downLeftButton = new SectionButton(TileSection.DownLeft);
        SectionButton downCenterButton = new SectionButton(TileSection.DownCenter);
        SectionButton downRightButton = new SectionButton(TileSection.DownRight);

        SectionButton centerButton = new SectionButton(TileSection.Center);

        //TODO controllare se li ho messi giusti, ho qualche dubbio
        HorizontalSectionButtonContainer upPanel = new HorizontalSectionButtonContainer(upRightButton, upCenterButton, upLeftButton);
        VerticalSectionButtonContainer leftPanel = new VerticalSectionButtonContainer(leftUpButton, leftCenterButton, leftDownButton);
        VerticalSectionButtonContainer rightPanel = new VerticalSectionButtonContainer(rightUpButton, rightCenterButton, rightDownButton);
        HorizontalSectionButtonContainer downPanel = new HorizontalSectionButtonContainer(downRightButton, downCenterButton, downLeftButton);
        CenterSectionButtonContainer centerPanel = new CenterSectionButtonContainer(centerButton);

        // this.add(upPanel, BorderLayout.NORTH);
        // this.add(leftPanel, BorderLayout.WEST);
        // this.add(rightPanel, BorderLayout.EAST);
        // this.add(downPanel, BorderLayout.SOUTH);
        // this.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints constr = new GridBagConstraints();
        //constr.fill = GridBagConstraints.VERTICAL;
        constr.weightx = 1;
        constr.weighty = 1;
        constr.gridx = 1;
        constr.gridy = 0;
        container.add(upPanel, constr);

        //constr.fill = GridBagConstraints.WEST;
        constr.gridx = 0;
        constr.gridy = 1;
        container.add(leftPanel, constr);

        //constr.fill = GridBagConstraints.CENTER;
        constr.gridx = 1;
        constr.gridy = 1;
        container.add(centerPanel, constr);

        //constr.fill = GridBagConstraints.EAST;
        constr.gridx = 2;
        constr.gridy = 1;
        container.add(rightPanel, constr);

        //constr.fill = GridBagConstraints.NORTH;
        constr.gridx = 1;
        constr.gridy = 2;
        container.add(downPanel, constr);
    }

    private String getLabelFromSection(TileSection section) {
        //return currentTile.getGameSet(section).getType().toString() + "-" + section.name();
        return "x";
    }

    private class SectionButton extends JButton {
        private final TileSection section;

        public SectionButton(TileSection section) {
            super();
            this.section = section;
            // TODO cambiare sta roba ovviamente
            String buttonLabel = getLabelFromSection(section);
            this.setText(buttonLabel);

            // this.setOpaque(false);
            // this.setContentAreaFilled(false);
            //this.setBorderPainted(false);

            this.addActionListener((e) ->
            {
                currentSectionSelected = Optional.of(this.section);
            });
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            int s = (int)(d.getWidth()<d.getHeight() ? d.getHeight() : d.getWidth());
            return new Dimension (s,s);
        }
    }

    private class VerticalSectionButtonContainer extends JPanel {
        public VerticalSectionButtonContainer(Component firstComponent, Component secondComponent,
                Component thirdComponent) {
            super.setOpaque(false);
            this.setLayout(new BorderLayout());
            this.add(new SectionButtonContainer(firstComponent, ButtonPosition.VerticalUp), BorderLayout.SOUTH);
            this.add(new SectionButtonContainer(secondComponent, ButtonPosition.AnyCenter), BorderLayout.CENTER);
            this.add(new SectionButtonContainer(thirdComponent, ButtonPosition.VerticalBottom), BorderLayout.NORTH);
        }
    };



    private enum ButtonPosition {
        //(int top, int left, int bottom, int right)
        VerticalBottom(10),
        VerticalUp(10),
        HorizontalLeft(10),
        Horizontalright(10),
        AnyCenter(10);

        int multiplier;
        ButtonPosition(int multiplier) {
            this.multiplier = multiplier;
        }

        public Border getBorder(int size){
            int amount = size/multiplier;
            return switch (this) {
                case VerticalBottom -> BorderFactory.createEmptyBorder(00, 0, 0, 0);
                case VerticalUp -> BorderFactory.createEmptyBorder(0, 0, 0, 0);
                case HorizontalLeft -> BorderFactory.createEmptyBorder(0, 0, 0, 0);
                case Horizontalright -> BorderFactory.createEmptyBorder(0, 0, 0, 0);
                case AnyCenter -> BorderFactory.createEmptyBorder(0, 0, 0, 0);
            };
        }
    }

    private class HorizontalSectionButtonContainer extends JPanel {
        public HorizontalSectionButtonContainer(Component firstComponent, Component secondComponent,
                Component thirdComponent) {
            super.setOpaque(false);
            this.setLayout(new BorderLayout());
            this.add(new SectionButtonContainer(firstComponent, ButtonPosition.Horizontalright), BorderLayout.EAST);
            this.add(new SectionButtonContainer(secondComponent, ButtonPosition.AnyCenter), BorderLayout.CENTER);
            this.add(new SectionButtonContainer(thirdComponent, ButtonPosition.HorizontalLeft), BorderLayout.WEST);
        }
    };

    private class SectionButtonContainer extends JPanel {
        public SectionButtonContainer(Component Component, ButtonPosition buttonPosition) {
            super();
            this.setOpaque(false);
            this.setLayout(new GridBagLayout());
            this.add(Component);
            this.setBorder(buttonPosition.getBorder(1));
        }

    }

    private Dimension getTotalSize() {
        return this.getSize();
    }

    private class CenterSectionButtonContainer extends JPanel {

        public CenterSectionButtonContainer(Component component) {
            this.setLayout(new GridLayout(0,1));
            this.setOpaque(false);
            this.add(new SectionButtonContainer(component, ButtonPosition.AnyCenter));
        }
    };

}
