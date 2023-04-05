package it.unibo.caesena.view.components;


import it.unibo.caesena.view.BasicComponent;

/**
 * This interface rappresents a sidebar to be used in Scene.
 *
 * This component contains the JButtons that will allow to control the BoardComponent:
 * the Zoom+/Zoom- buttons, the Movement arrows buttons, the button to place the current tile,
 * the button to place the player meeple, the button to discard the current tile and the button to end the turn
 *
 * @param <X> the type of the GUI component
 */
public interface SideBarBasicComponent<X> extends BasicComponent<X> {

}
