package myMaven01;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class loginProcedure {
    //实例化工具类进行密码加密
    SHA256Util sha256Util = new SHA256Util();
    static String path_name = "D:\\temp\\userInfo.txt";

    // TODO Auto-generated method stub
    public static void main(String args[]){

        //写入初始账户密码
        write_ByLine(path_name);


        Scanner scan = new Scanner(System.in);
        System.out.println("请输入帐号:");
        String account = scan.nextLine();
        System.out.println("请输入密码");
        String psd = scan.nextLine();

        //在后台核对密码
        boolean b  = chargeUserPsd(account,psd);
        if(b){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }
    }

    /**
     * 核对密码是否正确
     * @param account
     * @param inputPsd
     * @return
     */
    public static  boolean chargeUserPsd(String account,String inputPsd){
        boolean flag = false;
        String psd= SHA256Util.getSHA256String(inputPsd);
        String[][] accPsds = devidedAccountPsd(read_ByLine(path_name));
        //遍历读出的帐号密码信息，并进行匹配
        for(int i = 0;i<accPsds.length;i++){
            if(accPsds[i][0].equals(account)){
                if (accPsds[i][1].equals(psd)){
                    System.out.println(" 密码比对成功，用户输入密码："+inputPsd+"->" +psd + "  后台加载密码：" + accPsds[i][1]);
                    flag = true;
                    break;
                }
            }
        }
        return  flag;
    }


    /**
     * 将账户与密码分开存放
     * @param userInfoList
     * @return
     */
    public static String[][] devidedAccountPsd(List<String> userInfoList){
        String[][] accPsds = new String[userInfoList.size()][];
        int i = 0;
        for (String line:userInfoList){
            accPsds[i++] = line.split(":");
        }
        return accPsds;
    }

    /**
     * 根据行读取文件
     * @param path_name
     */
    public static List<String> read_ByLine(String path_name) {
        List<String> userInfoList = new ArrayList<>();
        FileReader reader = null;
        BufferedReader br = null;
        try {
            String str = null;
            reader = new FileReader(path_name);
            br = new BufferedReader(reader);
            while((str = br.readLine())!= null) {
                userInfoList.add(str);
            }
        }catch (Exception e) {
            // TODO: handle exception
        }
        return userInfoList;
    }

    /**
     * 按行写入文件
     * @param path_name
     */
    public static void write_ByLine(String path_name) {
        //声明文件写入以及写入缓存
        FileWriter writer = null;
        BufferedWriter bw = null;

        try {
            //判断文件是否存在
            File file = new File(path_name);
            if(!file.exists()) {
                file.createNewFile();
            }
            //定义以追加的方式写入字符串
            writer = new FileWriter(path_name,true);
            bw = new BufferedWriter(writer);

            //将帐号密码写入
            for (int i = 16201501;i<16201537;i++){
                String str;
                str = i+":"+ SHA256Util.getSHA256String("123");
                //按行写入目标文件
                bw.newLine();
                bw.write(str);
            }
            bw.close();
            writer.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }



}
