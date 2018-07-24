package com.moxie.crawler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moxie.client.manager.MoxieCallBack;
import com.moxie.client.manager.MoxieCallBackData;
import com.moxie.client.manager.MoxieContext;
import com.moxie.client.manager.MoxieSDK;
import com.moxie.client.model.MxParam;


/**
 * Created by taoweisong on 16/7/26.
 */
public class BigdataFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MoxieSDK";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bigdata, container, false);
        view.findViewById(R.id.btnEmail).setOnClickListener(this);
        view.findViewById(R.id.btnOnlineBank).setOnClickListener(this);
        view.findViewById(R.id.btnCarrier).setOnClickListener(this);
        view.findViewById(R.id.btnQzone).setOnClickListener(this);
        view.findViewById(R.id.btnAlipay).setOnClickListener(this);
        view.findViewById(R.id.btnTaobao).setOnClickListener(this);
        view.findViewById(R.id.btnJingdong).setOnClickListener(this);
        view.findViewById(R.id.btnBaoxian).setOnClickListener(this);
        view.findViewById(R.id.btnLinkedin).setOnClickListener(this);
        view.findViewById(R.id.btnChsi).setOnClickListener(this);
        view.findViewById(R.id.btnFund).setOnClickListener(this);
        view.findViewById(R.id.btnZX).setOnClickListener(this);
        view.findViewById(R.id.btnMaimai).setOnClickListener(this);
        view.findViewById(R.id.btnShiXinCourt).setOnClickListener(this);
        view.findViewById(R.id.btnZhiXingCourt).setOnClickListener(this);
        view.findViewById(R.id.btnTax).setOnClickListener(this);
        view.findViewById(R.id.btnIdCardQuery).setOnClickListener(this);
        view.findViewById(R.id.btnBankCardQuery).setOnClickListener(this);
        view.findViewById(R.id.btnCarrierQuery).setOnClickListener(this);
        view.findViewById(R.id.btnSecurity).setOnClickListener(this);
        view.findViewById(R.id.btn_sametrade).setOnClickListener(this);
        return view;
    }

    /**
     * @param v
     */
    public void onClick(View v) {

        int id = v.getId();
        try {
            final MainActivity mainActivity = (MainActivity) getActivity();

            MxParam mxParam = new MxParam();
            mxParam.setUserId(mainActivity.getSharedPreferValue("userId"));
            mxParam.setApiKey(mainActivity.getSharedPreferValue("apiKey"));

            switch (id) {
                /**
                 *  邮箱账单
                 *
                 *  1. 打开指定邮箱，并自动填入账号密码
                 *  JSONObject loginParams = new JSONObject();
                 *  loginParams.put("username", "moxie_vcredit");
                 *  loginParams.put("password", "moxie123456");
                 *  loginParams.put("sepwd", "way1love");     //独立密码, 目前只针对qq邮箱
                 *
                 *  HashMap<String, String> loginCustom = new HashMap<>();
                 *  loginCustom.put(MxParam.PARAM_CUSTOM_LOGIN_CODE, "qq.com");       // 通过loginCode打开指定的邮箱
                 *  loginCustom.put(MxParam.PARAM_CUSTOM_LOGIN_PARAMS, loginParams.toString());
                 *  mxParam.setLoginCustom(loginCustom);
                 *
                 */
                case R.id.btnEmail:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_EMAIL);
                    break;

                /**
                 *  网银
                 *
                 *  # 网银的六种登陆方式
                 *      1. IDCARD : 身份证
                 *      2. CARDNO : 卡号
                 *      3. MOBILE : 手机
                 *      4. USERNAM : 用户名
                 *      5. DEBITCARDNO : 储蓄卡卡号
                 *      6. CREDITCARDNO : 信用卡卡号
                 *
                 * ---- 注意: 假如loginParams传了DEBITCARDNO 或 CREDITCARDNO，一定要传CARDNO ----
                 *
                 *  # 通用格式传参方式，Json格式
                 *  loginCustom: { 
                 *    loginCode:"CMB",                  //要打开的银行
                 *    loginType:"CREDITCARD",           //要打开的银行类型（储蓄卡或者信用卡）
                 *    loginParams:{ 
                 *          "IDCARD":{                  //预填账号密码（密码可不填）
                 *              "username":"xxxx" ,
                 *              "password":"xxx"
                 *           } ,
                 *           "MOBILE":{
                 *              "username":"xxx",
                 *              "password":"xxx"
                 *           }
                 *     }
                 *   }
                 *
                 *
                 *  # 自定义参数
                 *  1.打开储蓄卡列表页面
                 *  mxParam.setLoginType(MxParam.PARAM_ITEM_TYPE_DEBITCARD);
                 *
                 *  2.打开信用卡列表页面
                 *  mxParam.setLoginType(MxParam.PARAM_ITEM_TYPE_CREDITCARD);
                 *
                 *  3.直达指定的银行
                 *  HashMap<String, String> loginCustomBank = new HashMap<String, String>();
                 *  loginCustomBank.put(MxParam.PARAM_CUSTOM_LOGIN_TYPE, MxParam.PARAM_ITEM_TYPE_CREDITCARD);
                 *  loginCustomBank.put(MxParam.PARAM_CUSTOM_LOGIN_CODE, "CMB");
                 *
                 *  4.通过传入loginCustom参数来指定登录的方式, 具体json格式见上面
                 *      3.1 直达银行页面并预填账户或密码
                 *      JSONObject loginInfoIdCard = new JSONObject();
                 *      loginInfoIdCard.put("username", "330681198109127499");
                 *      JSONObject loginParamsBank = new JSONObject();
                 *      //参考网银的几种登录方式
                 *      loginParamsBank.put(MxParam.PARAM_CUSTOM_LOGIN_PARAMS_IDCARD, loginInfoIdCard);
                 *
                 *      HashMap<String, String> loginCustomBank = new HashMap<String, String>();
                 *      loginCustomBank.put(MxParam.PARAM_CUSTOM_LOGIN_TYPE, MxParam.PARAM_ITEM_TYPE_CREDITCARD);
                 *      loginCustomBank.put(MxParam.PARAM_CUSTOM_LOGIN_CODE, "CMB");
                 *      //是否显示其他的登录方式，默认显示.  设置MxParam.PARAM_COMMON_YES会隐藏其他登录方式
                 * //   loginCustomBank.put(MxParam.PARAM_CUSTOM_LOGIN_OTHERS_HIDE, MxParam.PARAM_COMMON_YES);
                 *      loginCustomBank.put(MxParam.PARAM_CUSTOM_LOGIN_PARAMS, loginParamsBank.toString());
                 *
                 *      mxParam.setLoginCustom(loginCustomBank);
                 *
                 */
                case R.id.btnOnlineBank:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_ONLINEBANK);
                    break;
                /**
                 *  运营商
                 *
                 *  # 通用格式传参方式，Json格式
                 *  loginCustom: { 
                 *    loginParams:{ 
                 *          "name":"xxx",
                 *          "idcard":"xxx",
                 *          "phone":"xxx",
                 *     }
                 *     "editable":true
                 *   }
                 *
                 *  1. 手机号、身份信息预填
                 *  HashMap<String, String> extendParam = new HashMap<String, String>();
                 *  extendParam.put(MxParam.PARAM_CARRIER_IDCARD, "370205197405213513");        // 身份证
                 *  extendParam.put(MxParam.PARAM_CARRIER_PHONE, "13588888888");                // 手机号
                 *  extendParam.put(MxParam.PARAM_CARRIER_NAME, "普京大帝");                     // 姓名
                 *  extendParam.put(MxParam.PARAM_CARRIER_EDITABLE, MxParam.PARAM_COMMON_NO);   // 是否允许用户修改以上信息
                 *  mxParam.setExtendParams(extendParam);
                 *
                 */
                case R.id.btnCarrier:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_CARRIER);
                    break;
                /**
                 *  QQ
                 */
                case R.id.btnQzone:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_QQ);
                    break;
                /**
                 *  支付宝
                 */
                case R.id.btnAlipay:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_ALIPAY);
                    break;
                /**
                 *  淘宝
                 */
                case R.id.btnTaobao:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_TAOBAO);
                    break;
                /**
                 *  京东
                 */
                case R.id.btnJingdong:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_JINGDONG);
                    break;
                /**
                 *  车险
                 */
                case R.id.btnBaoxian:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_INSURANCE);
                    break;
                /**
                 *  学信网
                 */
                case R.id.btnChsi:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_CHSI);
                    break;
                /**
                 *  公积金
                 *
                 *  # 通用格式传参方式，Json格式
                 *  loginCustom: { 
                 *    loginCode:"310000",                  //指定要打开的城市
                 *    loginType:"CREDITCARD",              //要打开的银行类型（储蓄卡或者信用卡）
                 *    loginParams:{ 
                 *          "1":{                          //预填账号密码，"1"代表其中一种登录方式，根据实际情况传入
                 *              "account":"xxxx" ,         //这个key不一定是account,有可能是其他值，根据实际情况传入
                 *              "password":"xxx"
                 *           } 
                 *     }
                 *   }
                 *
                 *  # 自定义参数
                 *  1.直达指定城市
                 *  HashMap<String, String> loginCustom = new HashMap<>();
                 *  loginCustom.put(MxParam.PARAM_CUSTOM_LOGIN_CODE, "310000");
                 *  mxParam.setLoginCustom(loginCustom);
                 *
                 *  2.直达指定城市病预填账号，loginCustom的格式见上面
                 *  JSONObject userParams = new JSONObject();
                 *  userParams.put("account", "123456789");
                 *  userParams.put("password", "moxie123456");
                 *  JSONObject loginParams = new JSONObject();
                 *  loginParams.put("1", userParams);
                 *
                 *  HashMap<String, String> loginCustom = new HashMap<>();
                 *  loginCustom.put(MxParam.PARAM_CUSTOM_LOGIN_CODE, "310000");
                 *  loginCustom.put(MxParam.PARAM_CUSTOM_LOGIN_PARAMS, loginParams.toString());
                 *  mxParam.setLoginCustom(loginCustom);
                 */
                case R.id.btnFund:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_FUND);
                    break;
                /**
                 *  社保
                 *
                 *  见公积金
                 */
                case R.id.btnSecurity:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_SECURITY);
                    break;
                /**
                 *  征信报告
                 *
                 *  注意，这个业务不建议设置quitOnLoginDone
                 */
                case R.id.btnZX:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_ZHENGXIN);
                    break;
                /**
                 *  脉脉
                 */
                case R.id.btnMaimai:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_MAIMAI);
                    break;
                /**
                 *  法院失信人
                 */
                case R.id.btnShiXinCourt:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_SHIXINCOURT);
                    break;
                /**
                 *  法院执行人
                 */
                case R.id.btnZhiXingCourt:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_ZHIXINGCOURT);
                    break;
                /**
                 *  领英
                 */
                case R.id.btnLinkedin:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_LINKEDIN);
                    break;
                /**
                 *  个人所得税
                 */
                case R.id.btnTax:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_TAX);
                    break;
                /**
                 *  同业爬取
                 *
                 *  1. 传入基本信息
                 *  JSONObject loginParams = new JSONObject();
                 *  loginParams.put(MxParam.PARAM_IDCARD, "370205197405213513");                // 身份证
                 *  loginParams.put(MxParam.PARAM_PHONE, "13588888888");                        // 手机号
                 *  loginParams.put(MxParam.PARAM_NAME, "普京大帝");                             // 姓名
                 *  loginParams.put(MxParam.PARAM_PASSWORD, "123456");                          // 密码
                 *
                 *  HashMap<String, String> loginCustomSameTrade = new HashMap<>();
                 *  loginCustomSameTrade.put(MxParam.PARAM_CUSTOM_LOGIN_PARAMS, loginParams.toString());
                 *
                 *  mxParam.setLoginCustom(loginCustomSameTrade);
                 */
                case R.id.btn_sametrade:
                    mxParam.setFunction(MxParam.PARAM_FUNCTION_SAMETRADE);

                    break;
                //以下为身份验证
                case R.id.btnIdCardQuery:
                    mainActivity.IdentityVerification("idcard", "身份证认证");
                    return;
                case R.id.btnBankCardQuery:
                    mainActivity.IdentityVerification("bank", "银行卡四要素");
                    return;
                case R.id.btnCarrierQuery:
                    mainActivity.IdentityVerification("carrier", "运营商三要素");
                    return;

            }

            MoxieSDK.getInstance().start(getActivity(), mxParam, new MoxieCallBack() {
                /**
                 *
                 *  物理返回键和左上角返回按钮的back事件以及sdk退出后任务的状态都通过这个函数来回调
                 *
                 * @param moxieContext       可以用这个来实现在魔蝎的页面弹框或者关闭魔蝎的界面
                 * @param moxieCallBackData  我们可以根据 MoxieCallBackData 的code来判断目前处于哪个状态，以此来实现自定义的行为
                 * @return                   返回true表示这个事件由自己全权处理，返回false会接着执行魔蝎的默认行为(比如退出sdk)
                 *
                 *   # 注意，假如设置了MxParam.setQuitOnLoginDone(MxParam.PARAM_COMMON_YES);
                 *   登录成功后，返回的code是MxParam.ResultCode.IMPORTING，不是MxParam.ResultCode.IMPORT_SUCCESS
                 */
                @Override
                public boolean callback(MoxieContext moxieContext, MoxieCallBackData moxieCallBackData) {
                    /**
                     *  # MoxieCallBackData的格式如下：
                     *  1.1.没有进行账单导入，未开始！(后台没有通知)
                     *      "code" : MxParam.ResultCode.IMPORT_UNSTART, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "", "loginDone": false, "businessUserId": ""
                     *  1.2.平台方服务问题(后台没有通知)
                     *      "code" : MxParam.ResultCode.THIRD_PARTY_SERVER_ERROR, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  1.3.魔蝎数据服务异常(后台没有通知)
                     *      "code" : MxParam.ResultCode.MOXIE_SERVER_ERROR, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  1.4.用户输入出错（密码、验证码等输错且未继续输入）
                     *      "code" : MxParam.ResultCode.USER_INPUT_ERROR, "taskType" : "mail", "taskId" : "", "message" : "密码错误", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  2.账单导入失败(后台有通知)
                     *      "code" : MxParam.ResultCode.IMPORT_FAIL, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  3.账单导入成功(后台有通知)
                     *      "code" : MxParam.ResultCode.IMPORT_SUCCESS, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": true, "businessUserId": "xxxx"
                     *  4.账单导入中(后台有通知)
                     *      "code" : MxParam.ResultCode.IMPORTING, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": true, "businessUserId": "xxxx"
                     *
                     *  code           :  表示当前导入的状态
                     *  taskType       :  导入的业务类型，与MxParam.setFunction()传入的一致
                     *  taskId         :  每个导入任务的唯一标识，在登录成功后才会创建
                     *  message        :  提示信息
                     *  account        :  用户输入的账号
                     *  loginDone      :  表示登录是否完成，假如是true，表示已经登录成功，接入方可以根据此标识判断是否可以提前退出
                     *  businessUserId :  第三方被爬取平台本身的userId，非商户传入，例如支付宝的UserId
                     */
                    if (moxieCallBackData != null) {
                        Log.d("BigdataFragment", "MoxieSDK Callback Data : "+ moxieCallBackData.toString());
                        switch (moxieCallBackData.getCode()) {
                            /**
                             * 账单导入中
                             *
                             * 如果用户正在导入魔蝎SDK会出现这个情况，如需获取最终状态请轮询贵方后台接口
                             * 魔蝎后台会向贵方后台推送Task通知和Bill通知
                             * Task通知：登录成功/登录失败
                             * Bill通知：账单通知
                             */
                            case MxParam.ResultCode.IMPORTING:
                                if(moxieCallBackData.isLoginDone()) {
                                    //状态为IMPORTING, 且loginDone为true，说明这个时候已经在采集中，已经登录成功
                                    Log.d(TAG, "任务已经登录成功，正在采集中，SDK退出后不会再回调任务状态，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");

                                } else {
                                    //状态为IMPORTING, 且loginDone为false，说明这个时候正在登录中
                                    Log.d(TAG, "任务正在登录中，SDK退出后不会再回调任务状态，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");
                                }
                                break;
                            /**
                             * 任务还未开始
                             *
                             * 假如有弹框需求，可以参考 {@link BigdataFragment#showDialog(MoxieContext)}
                             *
                             * example:
                             *  case MxParam.ResultCode.IMPORT_UNSTART:
                             *      showDialog(moxieContext);
                             *      return true;
                             * */
                            case MxParam.ResultCode.IMPORT_UNSTART:
                                Log.d(TAG, "任务未开始");
                                break;
                            case MxParam.ResultCode.THIRD_PARTY_SERVER_ERROR:
                                Toast.makeText(getContext(), "导入失败(平台方服务问题)", Toast.LENGTH_SHORT).show();
                                break;
                            case MxParam.ResultCode.MOXIE_SERVER_ERROR:
                                Toast.makeText(getContext(), "导入失败(魔蝎数据服务异常)", Toast.LENGTH_SHORT).show();
                                break;
                            case MxParam.ResultCode.USER_INPUT_ERROR:
                                Toast.makeText(getContext(), "导入失败(" + moxieCallBackData.getMessage() + ")", Toast.LENGTH_SHORT).show();
                                break;
                            case MxParam.ResultCode.IMPORT_FAIL:
                                Toast.makeText(getContext(), "导入失败", Toast.LENGTH_SHORT).show();
                                break;
                            case MxParam.ResultCode.IMPORT_SUCCESS:
                                Log.d(TAG, "任务采集成功，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");

                                //根据taskType进行对应的处理
                                switch (moxieCallBackData.getTaskType()) {
                                    case MxParam.PARAM_FUNCTION_EMAIL:
                                        Toast.makeText(getContext(), "邮箱导入成功", Toast.LENGTH_SHORT).show();
                                        break;
                                    case MxParam.PARAM_FUNCTION_ONLINEBANK:
                                        Toast.makeText(getContext(), "网银导入成功", Toast.LENGTH_SHORT).show();
                                        break;
                                    //.....
                                    default:
                                        Toast.makeText(getContext(), "导入成功", Toast.LENGTH_SHORT).show();
                                }
                                moxieContext.finish();
                                return true;
                        }
                    }
                    return false;
                }

                @Override
                public boolean onError(MoxieContext moxieContext, int errorCode, Throwable th) {
                    Log.e(TAG, "onError, throwable="+th.getMessage());
                    if(errorCode == MxParam.ErrorCode.SDK_OPEN_FAIL) {
                        moxieContext.addView(getErrorView(moxieContext));
                        return true;
                    }
                    return super.onError(moxieContext, errorCode, th);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //用来清理数据或解除引用
        MoxieSDK.getInstance().clear();
    }

    private View getErrorView(final MoxieContext moxieContext){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.error_layout, null);
        view.findViewById(R.id.error_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moxieContext.finish();
            }
        });
        return view;
    }

    private void showDialog(final MoxieContext context) {
        //Dialog等引用了MoxieContext.getContext的组件，要及时解除引用，不然容易发生内存泄露
        AlertDialog mDialog = new AlertDialog.Builder(context.getContext()).setMessage("确定要返回？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
