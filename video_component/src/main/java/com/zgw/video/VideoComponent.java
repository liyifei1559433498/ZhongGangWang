package com.zgw.video;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;

public class VideoComponent implements IComponent {

    @Override
    public String getName() {
        return "videoComponent";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "VideoActivity":
                CCUtil.navigateTo(cc, VideoActivity.class);
                CC.sendCCResult(cc.getCallId(), CCResult.success());
                break;
            default:
                //其它actionName当前组件暂时不能响应，可以通过如下方式返回状态码为-12的CCResult给调用方
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }

        return false;
    }
}
