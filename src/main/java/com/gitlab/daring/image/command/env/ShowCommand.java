package com.gitlab.daring.image.command.env;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.component.BaseCanvasFrame;

import static com.gitlab.daring.image.command.CommandScriptUtils.runCommand;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ShowCommand extends BaseCommand {

    final String title = arg(0, "");
    final String key = arg(1, "");
    final BaseCanvasFrame frame = new BaseCanvasFrame(title);

    public ShowCommand(String... params) {
        super(params);
        if (!isCacheable()) frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public boolean isCacheable() {
        return !title.isEmpty();
    }

    @Override
    public void execute(CommandEnv env) {
        String tv = env.eval(title);
        if (title.equals(tv)) {
            frame.showMat(env.getMat(key));
        } else {
            runCommand(env, "show", tv, key);
        }
    }

    @Override
    public void close() {
        frame.dispose();
    }

}
