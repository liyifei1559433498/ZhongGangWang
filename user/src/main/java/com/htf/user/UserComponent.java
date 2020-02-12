package com.htf.user;

import android.content.Context;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;
import com.htf.user.fragment.AccountFragment;
import com.htf.user.ui.LoginActivity;

public class UserComponent implements IComponent {

    @Override
    public String getName() {
        return "userComponent";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        Context context = cc.getContext();
        switch (actionName) {
            case "AccountFragment":
                CCResult result = new CCResult();
                result.addData("fragment", AccountFragment.newInstance());
                CC.sendCCResult(cc.getCallId(), result);
                return false;
            case "LoginActivity":
//                Intent intent = new Intent(context, LoginActivity.class);
//                if (!(context instanceof Activity)) {
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                intent.putExtra("callId", cc.getCallId());
//                context.startActivity(intent);
                CCUtil.navigateTo(cc, LoginActivity.class);
                return true;
            default:
                //其它actionName当前组件暂时不能响应，可以通过如下方式返回状态码为-12的CCResult给调用方
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }
    }
}
