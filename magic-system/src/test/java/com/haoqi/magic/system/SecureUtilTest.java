package com.haoqi.magic.system;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.google.common.collect.Maps;
import com.haoqi.rigger.core.error.RiggerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.crypto.Cipher;
import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author twg
 * @since 2019/5/10
 */
@Slf4j
public class SecureUtilTest {
    private static Map<String, AtomicInteger> payThreadLocal = Maps.newConcurrentMap();

    public static void main(String[] args) throws Exception {
        /*KeyPair keyPair = SecureUtil.generateKeyPair("RSA");

        String publicKeyBase64 = Base64.encode(keyPair.getPublic().getEncoded());
        String privateKeyBase64 = Base64.encode(keyPair.getPrivate().getEncoded());

        System.out.println("SysAdvertConfigTests.main == public === " + publicKeyBase64);
        System.out.println("SysAdvertConfigTests.main == private =" + privateKeyBase64);*/
        String d = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCewmdluDzqa9vQ9iMxySdCvlWC6lES9uZ43sxOQaQU6B6JJrzzFxNg2Uq2RFXn7J/f0rFSJ9UuhGUR+BRLC/vaCbuiSMvM0jsFxyYuRZbxrtaVM1gZXVKv6lA2XZa9nXc4RO8g1/tTuUMn1pW9iFBebhLuAGUPrTlr+3Z+UJcQ6wIDAQAB";
        //MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ7CZ2W4POpr29D2IzHJJ0K+VYLqURL25njezE5BpBToHokmvPMXE2DZSrZEVefsn9/SsVIn1S6EZRH4FEsL+9oJu6JIy8zSOwXHJi5FlvGu1pUzWBldUq/qUDZdlr2ddzhE7yDX+1O5QyfWlb2IUF5uEu4AZQ+tOWv7dn5QlxDrAgMBAAECgYBTnOd/9yx9+hhnUXrvuZVn3X6D2IyihE6V0iGgBYo5SJbsfO5+yOR32XkzzYHBCCqbYwfo0PnB6hChChsF+2XEd4erkFm/QrvcvlnMlSdg38fzP8HLu2ft0sWtyV4Em2rKtsjY1wwGcVM2KzoDIwYXoAOXhauz0Tp0fO419UpFgQJBAMsfsV2jwFQPUnu+dld5gHgDh7MccGSdJehebibNMUyz61En64rl7fHkj4i6Px/7pYATbtzsH20xsApjJ3nrwbECQQDIFjyhL5ZS86mbR9V8lAH/T4PB4RbZxL/VegpR7zYIM210vt/cGox0fwZw7ESblkwfqsUdVkaier3vGPRgS2dbAkBBrpXj+bePdwTtDsGlt5xbMokG2gNeBZLkeOSVl3SBoQxOyeHYoFE5Dvd69v7CkNULfT00IwZmgNK0CSwSuLGBAkEAkakOjlE0YEMeb/rFgiHFqnXad2RD3FkNRn3H0YoRjbrSrjDeiz+QjKaEq+R0tceet1b8lLTgSxyL9On3zbpXDQJBAKdM88mj1OfpQzdxuZiGtZa3jds2pmoqMf6+RkLQY/zVyhj1ybXZQgN9BZTsIhNnnhqfrVpEX7aU9/iKCrVp0rA=

        String context = encrypt("111111", d);
        System.out.println("SecureUtilTest.main dd : " + context);
//       String keu =  SecureUtils.decryptRSAByPrivateKey(context,"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ7CZ2W4POpr29D2IzHJJ0K+VYLqURL25njezE5BpBToHokmvPMXE2DZSrZEVefsn9/SsVIn1S6EZRH4FEsL+9oJu6JIy8zSOwXHJi5FlvGu1pUzWBldUq/qUDZdlr2ddzhE7yDX+1O5QyfWlb2IUF5uEu4AZQ+tOWv7dn5QlxDrAgMBAAECgYBTnOd/9yx9+hhnUXrvuZVn3X6D2IyihE6V0iGgBYo5SJbsfO5+yOR32XkzzYHBCCqbYwfo0PnB6hChChsF+2XEd4erkFm/QrvcvlnMlSdg38fzP8HLu2ft0sWtyV4Em2rKtsjY1wwGcVM2KzoDIwYXoAOXhauz0Tp0fO419UpFgQJBAMsfsV2jwFQPUnu+dld5gHgDh7MccGSdJehebibNMUyz61En64rl7fHkj4i6Px/7pYATbtzsH20xsApjJ3nrwbECQQDIFjyhL5ZS86mbR9V8lAH/T4PB4RbZxL/VegpR7zYIM210vt/cGox0fwZw7ESblkwfqsUdVkaier3vGPRgS2dbAkBBrpXj+bePdwTtDsGlt5xbMokG2gNeBZLkeOSVl3SBoQxOyeHYoFE5Dvd69v7CkNULfT00IwZmgNK0CSwSuLGBAkEAkakOjlE0YEMeb/rFgiHFqnXad2RD3FkNRn3H0YoRjbrSrjDeiz+QjKaEq+R0tceet1b8lLTgSxyL9On3zbpXDQJBAKdM88mj1OfpQzdxuZiGtZa3jds2pmoqMf6+RkLQY/zVyhj1ybXZQgN9BZTsIhNnnhqfrVpEX7aU9/iKCrVp0rA=");
//
//        String string = decryptByPrivateKey(context, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ7CZ2W4POpr29D2IzHJJ0K+VYLqURL25njezE5BpBToHokmvPMXE2DZSrZEVefsn9/SsVIn1S6EZRH4FEsL+9oJu6JIy8zSOwXHJi5FlvGu1pUzWBldUq/qUDZdlr2ddzhE7yDX+1O5QyfWlb2IUF5uEu4AZQ+tOWv7dn5QlxDrAgMBAAECgYBTnOd/9yx9+hhnUXrvuZVn3X6D2IyihE6V0iGgBYo5SJbsfO5+yOR32XkzzYHBCCqbYwfo0PnB6hChChsF+2XEd4erkFm/QrvcvlnMlSdg38fzP8HLu2ft0sWtyV4Em2rKtsjY1wwGcVM2KzoDIwYXoAOXhauz0Tp0fO419UpFgQJBAMsfsV2jwFQPUnu+dld5gHgDh7MccGSdJehebibNMUyz61En64rl7fHkj4i6Px/7pYATbtzsH20xsApjJ3nrwbECQQDIFjyhL5ZS86mbR9V8lAH/T4PB4RbZxL/VegpR7zYIM210vt/cGox0fwZw7ESblkwfqsUdVkaier3vGPRgS2dbAkBBrpXj+bePdwTtDsGlt5xbMokG2gNeBZLkeOSVl3SBoQxOyeHYoFE5Dvd69v7CkNULfT00IwZmgNK0CSwSuLGBAkEAkakOjlE0YEMeb/rFgiHFqnXad2RD3FkNRn3H0YoRjbrSrjDeiz+QjKaEq+R0tceet1b8lLTgSxyL9On3zbpXDQJBAKdM88mj1OfpQzdxuZiGtZa3jds2pmoqMf6+RkLQY/zVyhj1ybXZQgN9BZTsIhNnnhqfrVpEX7aU9/iKCrVp0rA=");
//
//        System.out.println("SecureUtilTest.main context is  " + string);

        String json = "{\"belongTo\":1,\"buyerInfo\":\"jii\",\"carChecks\":[{\"checkItemName\":\"异常检查\",\"checkItemText\":\"右后车身：右后轮胎\",\"csCarCheckItemId\":1035,\"csCarCheckLastItemId\":\"235\",\"fileName\":\"15590127833696.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspbOAfzujAAkcI_rLQmI31.JPEG\"},{\"checkItemName\":\"外观检查\",\"checkItemText\":\"右前门：虚漆\",\"csCarCheckItemId\":1036,\"csCarCheckLastItemId\":\"564\",\"fileName\":\"1559012797999428.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspcCATTY3AAkcI_rLQmI41.JPEG\"},{\"checkItemName\":\"车内功能检查项\",\"checkItemText\":\"左前后视镜：卡滞，故障\",\"csCarCheckItemId\":4,\"csCarCheckLastItemId\":\"807,806\",\"fileName\":\"155901281112593.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspcuAW81FAAmoKI6rWa062.JPEG\"},{\"checkItemName\":\"异常检查\",\"checkItemText\":\"右后车身：右后轮胎\",\"csCarCheckItemId\":1035,\"csCarCheckLastItemId\":\"235\",\"fileName\":\"15590127833696.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspbOAfzujAAkcI_rLQmI31.JPEG\"},{\"checkItemName\":\"外观检查\",\"checkItemText\":\"右前门：虚漆\",\"csCarCheckItemId\":1036,\"csCarCheckLastItemId\":\"564\",\"fileName\":\"1559012797999428.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspcCATTY3AAkcI_rLQmI41.JPEG\"},{\"checkItemName\":\"车内功能检查项\",\"checkItemText\":\"左前后视镜：卡滞，故障\",\"csCarCheckItemId\":4,\"csCarCheckLastItemId\":\"807,806\",\"fileName\":\"155901281112593.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspcuAW81FAAmoKI6rWa062.JPEG\"},{\"checkItemName\":\"外观检查\",\"checkItemText\":\"右前门：虚漆\",\"csCarCheckItemId\":1036,\"csCarCheckLastItemId\":\"564\",\"fileName\":\"1559012797999428.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspcCATTY3AAkcI_rLQmI41.JPEG\"},{\"checkItemName\":\"车内功能检查项\",\"checkItemText\":\"左前后视镜：卡滞，故障\",\"csCarCheckItemId\":4,\"csCarCheckLastItemId\":\"807,806\",\"fileName\":\"155901281112593.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspcuAW81FAAmoKI6rWa062.JPEG\"}],\"carConfig\":{\"airBag\":\"high\",\"airCondition\":1,\"dlcc\":2,\"haveAbs\":1,\"haveTurnEngine\":1,\"hubCode\":\"290003\",\"musicType\":2,\"navigate\":2,\"pdcCode\":\"270004\",\"rearviewMirrorType\":1,\"rvcCode\":\"280004\",\"seatAdjustTypeCode\":\"250003\",\"seatFunctionCode\":\"260004\",\"seatMaterialCode\":\"240004\",\"skyWindowCode\":\"230005\",\"windowGlassCode\":\"220004\"},\"carDealer\":\"ppppp\",\"carFactory\":\"hjki\",\"carFiles\":[{\"fileChildTypeCode\":\"Y_150001\",\"fileChildTypeName\":\"左前45°\",\"fileName\":\"1559012698365192.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspVyAAcZCAAddia8prt878.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150002\",\"fileChildTypeName\":\"右后45°\",\"fileName\":\"1559012705616806.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspWOAXLk9AAddia8prt823.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150003\",\"fileChildTypeName\":\"主副驾驶椅\",\"fileName\":\"1559012710980645.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspWqAE5KMAAmoKI6rWa027.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150004\",\"fileChildTypeName\":\"后排座椅\",\"fileName\":\"1559012721531802.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspXSAfG0LAAmoKI6rWa043.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150005\",\"fileChildTypeName\":\"中控台\",\"fileName\":\"1559012729136320.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspXqAe-5iAAmoKI6rWa031.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150006\",\"fileChildTypeName\":\"仪表盘\",\"fileName\":\"155901273369135.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspX6AAxmwAAmoKI6rWa049.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150007\",\"fileChildTypeName\":\"发动机舱左\",\"fileName\":\"1559012738364960.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspYOAEUwtAAmoKI6rWa031.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150008\",\"fileChildTypeName\":\"发动机舱右\",\"fileName\":\"1559012743162771.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspYeACvNzAAmoKI6rWa032.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150009\",\"fileChildTypeName\":\"发动机舱\",\"fileName\":\"1559012747514445.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspYyAU0gSAAmoKI6rWa034.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150010\",\"fileChildTypeName\":\"后备箱\",\"fileName\":\"1559012752703274.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspZGAeRcfAAddia8prt860.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150011\",\"fileChildTypeName\":\"钥匙\",\"fileName\":\"1559012757050418.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspZWAE309AAmoKI6rWa050.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150012\",\"fileChildTypeName\":\"轮胎\",\"fileName\":\"1559012761568177.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspZqAZ0WMAAddia8prt884.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"Y_150013\",\"fileChildTypeName\":\"备胎槽\",\"fileName\":\"1559012766782318.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspZ-AdkpkAAmoKI6rWa036.JPEG\",\"fileType\":1},{\"fileChildTypeCode\":\"N_210001\",\"fileChildTypeName\":\"行驶证\",\"fileName\":\"1559012773089268.JPEG\",\"filePath\":\"group1/M00/02/C2/wKgB8lzspaaAYnxXAAkcI_rLQmI68.JPEG\",\"fileType\":2}],\"carProcedure\":{\"buyBill\":1,\"haveBreakRuleRecord\":1,\"haveDriveCard\":1,\"haveImportOrder\":1,\"haveNamePlate\":1,\"havePurchaseTax\":1,\"haveRegisterCard\":1,\"haveSpareKey\":1,\"haveVehicleVesselTax\":1,\"surface\":1,\"tciPosition\":\"内蒙古自治区-乌海市\",\"tciValidDate\":\"2019-05-28\",\"transferBill\":1,\"validDate\":\"2019-05-28\"},\"carTypeCode\":\"110004\",\"carVersion\":\"nj\",\"colorCode\":\"140003\",\"csCarDealerId\":4,\"displacement\":\"3\",\"driveMethodCode\":\"120003\",\"emissionStandardCode\":\"160007\",\"engineNo\":\"jj\",\"fuelTypeCode\":\"130005\",\"gearBoxCode\":\"100003\",\"haveFixedPrice\":1,\"havePromotePrice\":1,\"importType\":1,\"initDate\":\"2019-05-28\",\"instrumentShowDistance\":\"66\",\"locate\":\"黑龙江省-鸡西市\",\"otherInfo\":\"jkk\",\"plateNo\":\"200001\",\"price\":\"66\",\"productDate\":\"2019-05-28\",\"registerLocate\":\"北京市-北京市\",\"seatNum\":\"3\",\"sellerInfo\":\"jjk\",\"spareWheel\":1,\"suggestPrice\":\"66\",\"sysCarBrandName\":\"奥迪\",\"sysCarModelId\":59,\"sysCarModelName\":\"2012款 奥迪A6L TFSI 标准型\",\"sysCarSeriesName\":\"奥迪A6L\",\"transferNum\":\"66\",\"transferType\":\"uu\",\"travelDistance\":\"66\",\"tyreType\":\"kk\",\"useType\":1,\"vin\":\"GFFGGGY456FGGGGGY\",\"wholesalePrice\":\"533\"}";

        String userType = "$2a$10$YAa.E1WasPMOkyhFamc55OQUB7FKxzk8pwwNoyqzujdKG7dogT11e";


//        System.out.println("SecureUtilTest.main duserType : " + userType + "  " + DigestUtil.bcryptCheck("4", userType));


        /*String pass = "123456www";
        String salt = "ybtpu51b1amcrgqbpp0gp6htrdz3ql0v";
        String encod = DigestUtil.bcrypt(pass + salt);
        System.out.println("SecureUtilTest.main encod ====== " + encod);

        boolean r = DigestUtil.bcryptCheck(pass + salt, "$2a$10$hQUi2aij4Nm8iyI6KHrRNeczfPzdYtHuO6vwLWZlRW06IRYgmsyN.");
        System.out.println("SecureUtilTest.main r ====== " + r);*/

//        for (int i = 0; i < 100; i++) {
//            confirmPaid("P1221212", "12");
//        }


//        boolean b = DateUtil.beginOfDay(DateUtil.date()).after(DateUtil.parseDate("2020-01-07"));

//        String context1 = SecureUtils.decryptRSAByPrivateKey(context, Constants.privateKey);

        String pss = "qwe123456";
        String salt = "npw7e89do6ei386z3cmwmeju5ckvchkk";

        boolean r = DigestUtil.bcryptCheck(pss + salt, "$2a$10$kvdEi86u.k6ylpc1Co97EuxLTAis7cl3kfELuyU2xWMyZ8mtcR4/G");


        Integer a = 15;
        String b = "0.4";

        System.out.println("SecureUtilTest.main " + number(a.toString(),b));
    }


    public static int number(String v1, String v2) {
        BigDecimal decimal = NumberUtil.div(v1, v2, 2);
        String s = StrUtil.subAfter(decimal.toString(), StrUtil.DOT, false);
        int r = decimal.intValue();
        if (NumberUtil.parseInt(s) != 0) {
            r = decimal.intValue() + 1;
        }
        return r;
    }

    private static Boolean confirmPaid(String serialNo, String payCode) {
        if (RandomUtil.randomInt(10) == 5) {
            return Boolean.TRUE;
        }
        boolean flag = false;
        try {
            flag = getBooleanFuture(serialNo, payCode);
        } catch (Exception e) {
            log.error("支付确认异常：", e);
        }
        payThreadLocal.remove(serialNo);
        if (!flag) {
            throw new RiggerException("支付正在交易中，请稍后");
        }
        return Boolean.TRUE;
    }


    private static Boolean getBooleanFuture(String serialNo, String payCode) throws ExecutionException, InterruptedException, TimeoutException {
        BasicThreadFactory threadFactory = new BasicThreadFactory.Builder()
                .namingPattern("confirmPaid-%d")
                .uncaughtExceptionHandler(((t, e) -> {
                    log.error("支付确认线程：{}，异常信息：{}", t.getName(), e);
                }))
                .build();
        if (!payThreadLocal.containsKey(serialNo)) {
            payThreadLocal.put(serialNo, new AtomicInteger(1));
        } else {
            AtomicInteger integer = payThreadLocal.get(serialNo);
            integer.getAndAdd(1);
            payThreadLocal.put(serialNo, integer);
        }
        log.info("支付订单号：{}，循环次数：{}", serialNo, payThreadLocal.get(serialNo).get());
        if (payThreadLocal.get(serialNo).get() > 3) {
            return Boolean.FALSE;
        }
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, threadFactory);
        return executorService.schedule(() -> {
            return confirmPaid(serialNo, payCode);
        }, 3, TimeUnit.SECONDS).get(10, TimeUnit.SECONDS);
    }

    private static Boolean confirmPaid(String serialNo) throws ExecutionException, InterruptedException {
        BasicThreadFactory threadFactory = new BasicThreadFactory.Builder()
                .namingPattern("confirmPaid-%d")
                .uncaughtExceptionHandler(((t, e) -> {
                    log.error("支付确认线程：{}，异常信息：{}", t.getName(), e);
                }))
                .build();
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, threadFactory);
        return executorService.schedule(() -> {
            return handler(serialNo);
        }, 3, TimeUnit.SECONDS).get();

    }

    private static Boolean handler(String serialNo) {
        return Boolean.FALSE;

    }

    /**
     * 使用密钥进行解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String key) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = java.util.Base64.getDecoder().decode(data.getBytes("UTF-8"));
        // base64编码的私钥
        byte[] decoded = java.util.Base64.getDecoder().decode(key);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte), "UTF-8");

        /*PrivateKey privateKey = SecureUtil.generatePrivateKey("RSA", new PKCS8EncodedKeySpec(java.util.Base64.getDecoder().decode(key)));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(Base64.decode(data));
        return new String(bytes, "UTF-8");*/
    }

    public static String encrypt(String str, String publicKey) throws Exception {
        // base64编码的公钥
        byte[] decoded = java.util.Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        // RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
    }


}
