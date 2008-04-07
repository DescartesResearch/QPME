package de.tud.cs.qpe.editors.incidence.controller.command;

import org.dom4j.Element;
import org.eclipse.gef.commands.Command;

import de.tud.cs.qpe.model.DocumentManager;

/**
 * A command to disconnect (remove) a connection from its endpoints. The command
 * can be undone or redone.
 * 
 * @author Christofer Dutz
 */
public class ConnectionDeleteCommand extends Command {

	/** Connection instance to disconnect. */
	private Element connection;
	/** parent of the connection-element. */
	private Element parent;

	/**
	 * Create a command that will disconnect a connection from its endpoints.
	 * 
	 * @param conn
	 *            the connection instance to disconnect (non-null)
	 * @throws IllegalArgumentException
	 *             if conn is null
	 */
	public ConnectionDeleteCommand(Element connection) {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		setLabel("connection deletion");
		this.connection = connection;
		this.parent = connection.getParent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		// Remove the connection element from its
		// container.
		DocumentManager.removeElement(connection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		DocumentManager.addChild(parent, connection);
	}
}
