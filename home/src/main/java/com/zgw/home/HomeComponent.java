package com.zgw.home;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.zgw.home.fragment.HomeFragment;
import com.zgw.home.fragment.InformationFragment;
import com.zgw.home.fragment.StockMarketFragment;

public class HomeComponent implements IComponent {

    @Override
    public String getName() {
        return "homeComponent";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        CCResult result = new CCResult();
        switch (actionName) {
            case "HomeFragment":
                result.addData("fragment", HomeFragment.newInstance());
                CC.sendCCResult(cc.getCallId(), result);
                return false;
            case "InformationFragment":
                result.addData("fragment", InformationFragment.newInstance());
                CC.sendCCResult(cc.getCallId(), result);
                return false;
            case "StockMarketFragment":
                result.addData("fragment", StockMarketFragment.newInstance());
                CC.sendCCResult(cc.getCallId(), result);
                return false;
            default:
                //其它actionName当前组件暂时不能响应，可以通过如下方式返回状态码为-12的CCResult给调用方
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }
    }
}
