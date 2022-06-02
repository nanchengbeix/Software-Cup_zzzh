package com.ycu.zzzh.visual_impairment_3zh.utils;

import java.io.*;

import javax.sound.sampled.AudioFileFormat;

import javax.sound.sampled.AudioFormat;

import javax.sound.sampled.AudioInputStream;

import javax.sound.sampled.AudioSystem;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

/**

* 音频转换工具类

*

*/

public class AudioUtils {
    private static final long SAMPLE_RATE = 16000L;
    private static final int CHANNELS = 2;
    private static final int BYTE_SIZE = 1024;
    private static final int WAVE_HEADER_LENGTH = 44;
    /**

    * MP3转换PCM文件方法

    *

    * @param mp3filepath 原始文件路径

    * @param pcmfilepath 转换文件的保存路径

    * @return

    * @throws Exception

    */

    public static boolean convertMP32Pcm(String mp3filepath, String pcmfilepath){
        try {
        //获取文件的音频流，pcm的格式

        AudioInputStream audioInputStream = getPcmAudioInputStream(mp3filepath);

        //将音频转化为 pcm的格式保存下来

        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(pcmfilepath));

        return true;

        } catch (IOException e) {
        e.printStackTrace();

        return false;

        }
    }

    /**
     * wav转pcm
     * @param wavFile wav文件路径
     * @return pcm文件路径
     */
    public static String wavToPcmFilePath(String wavFile){
        try {
            byte[] buffer= new byte[1024];
            //wav 和pcm的区别就是wav在pcm的前面多了44字节
            byte[] preBuffer= new byte[44];
            int readByte = 0;
            FileInputStream fis = new FileInputStream(wavFile);
            String new_audio = wavFile.substring(0,wavFile.lastIndexOf(".")+1)+"pcm";
            FileOutputStream fos = new FileOutputStream(new_audio);
            //提出44位的wav前缀
            if (fis.read(preBuffer)==-1) {
                return null;
            }
            //复制pcm内容
            while((readByte = fis.read(buffer)) != -1) {
                fos.write(buffer,0,readByte);
            }
            fos.flush();
            fos.close();
            fis.close();
            return new_audio;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换方法
     * @param pcmFilePath pcm文件路径
     * @param waveFilePath wav文件路径
     * @return
     */
    public static Boolean audioPcmToWave(String pcmFilePath, String waveFilePath) {
        System.out.println(pcmFilePath + "to" + waveFilePath);
        long totalAudioLen, totalDateLen;

        long byteRate = 16 * SAMPLE_RATE * CHANNELS / 8;//64000
        byte[] data = new byte[BYTE_SIZE];

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(pcmFilePath);
            outStream = new FileOutputStream(waveFilePath);
            totalAudioLen = inStream.getChannel().size();
            totalDateLen = totalAudioLen + 36;
            byte[] waveFileHeader = getWaveFileHeader(totalAudioLen, totalDateLen, SAMPLE_RATE, CHANNELS, byteRate);
            outStream.write(waveFileHeader);
            while (inStream.read(data) != -1) {
                outStream.write(data);
            }
            inStream.close();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 生成头部
     * @param totalAudioLen 文件语音数据大小
     * @param totalDateLen 总文件大小
     * @param sampleRate 采样率
     * @param channels 声道数
     * @param byteRate 播放频率，数据缓存区大小
     * @return
     */
    private static byte[] getWaveFileHeader(long totalAudioLen, long totalDateLen, long sampleRate, int channels,
                                            long byteRate) {
        byte[] header = new byte[WAVE_HEADER_LENGTH];
        // RIFF/WAVE header
        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';

        header[4] = (byte) (totalDateLen & 0xff);
        header[5] = (byte) ((totalDateLen >> 8) & 0xff);
        header[6] = (byte) ((totalDateLen >> 16) & 0xff);
        header[7] = (byte) ((totalDateLen >> 24) & 0xff);

        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        // 'fmt' chunk
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        // 4bytes: size of 'fmt ' chunk
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        // format = 1
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;

        header[24] = (byte) (sampleRate & 0xff);
        header[25] = (byte) ((sampleRate >> 8) & 0xff);
        header[26] = (byte) ((sampleRate >> 16) & 0xff);
        header[27] = (byte) ((sampleRate >> 24) & 0xff);

        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        // block align
        header[32] = (byte) (2 * 16 / 8);
        header[33] = 0;
        header[34] = 16;
        header[35] = 0;
        // data
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        return header;
    }
    /**

    * 机能概要:获取文件的音频流

    * @param mp3filepath

    * @return

    */

    private static AudioInputStream getPcmAudioInputStream(String mp3filepath) {
    File mp3 = new File(mp3filepath);

    AudioInputStream audioInputStream = null;

    AudioFormat targetFormat = null;

    try {
    AudioInputStream in = null;

    //读取音频文件的类

    MpegAudioFileReader mp = new MpegAudioFileReader();

    in = mp.getAudioInputStream(mp3);

    AudioFormat baseFormat = in.getFormat();

    //设定输出格式为pcm格式的音频文件

    targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,

    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

    //输出到音频

    audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);

    } catch (Exception e) {
    e.printStackTrace();

    }

    return audioInputStream;

    }

}