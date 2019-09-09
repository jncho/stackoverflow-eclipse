package helpstack;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class Activator extends AbstractUIPlugin {

    private static Activator inst;

    public Activator() {
        if (inst == null)
            inst = this;
    }

    static public Activator getDefault() {
        return inst;
    }

}
