package com.steelnet.activity;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.zgw.base.model.RefreshEvent;
import com.zgw.base.util.RxBus;

public class ComponentApp implements IComponent {

    @Override
    public String getName() {
        return "componentApp";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "showMainActivity":
                break;
            case "refreshMsg":
                refreshMsgCount(cc);
                break;
            default:
                break;
        }

        return false;
    }

    private void refreshMsgCount(CC cc) {
        RxBus.getDefault().post(new RefreshEvent(cc.getParamItem("msgCount")));
        CC.sendCCResult(cc.getCallId(), CCResult.success());
    }

}
