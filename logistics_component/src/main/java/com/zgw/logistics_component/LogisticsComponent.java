package com.zgw.logistics_component;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

public class LogisticsComponent implements IComponent {

    @Override
    public String getName() {
        return "logistics_component";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "LogisticsFragment":
                CCResult result = new CCResult();
                result.addData("fragment", LogisticsMainFragment.newInstance());
                CC.sendCCResult(cc.getCallId(), result);
                return false;
            default:
                //其它actionName当前组件暂时不能响应，可以通过如下方式返回状态码为-12的CCResult给调用方
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }
    }
}
