package cn.mulanbay.ai.ml.simulate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * 模拟月度预算消费比例数据
 *
 * @author fenghong
 * @create 2023-06-27
 */
public class SimulateBudgetConsumeMData {

    public static void main(String[] args){
        //第一步：设置输出的文件路径
        //如果该目录下不存在该文件，则文件会被创建到指定目录下。如果该目录有同名文件，那么该文件将被覆盖。
        File writeFile = new File("/**/mulanbay-sklearn/datasets/budget_consume_m.csv");

        try{
            //第二步：通过BufferedReader类创建一个使用默认大小输出缓冲区的缓冲字符输出流
            BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile));

            //写入标题
            writeText.write("month,score,dayIndex,rate");
            for(int month = 1;month<=12;month++){
                for(int score = 0;score<=100;score++){
                    for(int dayIndex = 1;dayIndex<=31;dayIndex++){
                        double rate = dayIndex / 31.0 + Math.random()*0.1;
                        writeText.newLine();    //换行
                        //调用write的方法将字符串写到流中
                        writeText.write(month+","+score+","+dayIndex+","+rate);
                        System.out.println("写入一行");
                    }
                }
            }

            //使用缓冲区的刷新方法将数据刷到目的地中
            writeText.flush();
            //关闭缓冲区，缓冲区没有调用系统底层资源，真正调用底层资源的是FileWriter对象，缓冲区仅仅是一个提高效率的作用
            //因此，此处的close()方法关闭的是被缓存的流对象
            writeText.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
