package com.magnavi.util;

import android.util.Log;

public class mag_protocol
{
   byte[]ins1={(byte)0xf1,(byte)0x01,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1};
   byte[]ins2={(byte)0xf1,(byte)0x02,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1};
   byte[]ins3={(byte)0xf1,(byte)0x03,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1};
   byte[]ins4={(byte)0xf1,(byte)0x04,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1};
   byte[]ins5={(byte)0xf1,(byte)0x05,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1};
   byte[]ins6={(byte)0xf1,(byte)0x06,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1,(byte)0xf1};
	public void mag_protocol()
	{
		
	}
	
	public byte[] get_mag_power_status1(int low_address, int high_address)
	{
		//分配地址
		ins1[3]=(byte) (low_address&0x00ff);
		ins1[2]=(byte)((low_address>>8)&0x00ff);
		
		ins1[5]=(byte) (high_address&0x00ff);
		ins1[4]=(byte)((high_address>>8)&0x00ff);
		
		//校验CRC的samplecode
		//getCRC
		//hexStr2Byte
		//after should rewrite in one function
		String CRC = CRC16.getCRC(ins1, 6);
		Log.d("sCRC", "the string of crc is"+ CRC);
		byte[] str2byte = CRC16.hexStr2Bytes(CRC);
		ins1[7]= (byte)(str2byte[1]&0x00ff);
		ins1[6]= (byte)(str2byte[0]&0x00ff);
		return ins1;
	}
	public byte[] get_mag_power_status2(int low_address, int high_address)
	{
		//分配地址
		ins2[3]=(byte) (low_address&0x00ff);
		ins2[2]=(byte)((low_address>>8)&0x00ff);
		
		ins2[5]=(byte) (high_address&0x00ff);
		ins2[4]=(byte)((high_address>>8)&0x00ff);
		//校验CRC的samplecode
				//getCRC
				//hexStr2Byte
				//after should rewrite in one function
				String CRC = CRC16.getCRC(ins2, 6);
				Log.d("sCRC", "the string of crc is"+ CRC);
				byte[] str2byte = CRC16.hexStr2Bytes(CRC);
				ins2[7]= (byte)(str2byte[1]&0x00ff);
				ins2[6]= (byte)(str2byte[0]&0x00ff);
		
		return ins2;
	}
	public byte[] get_mag_power_status3(int low_address, int high_address)
	{
		//分配地址
		ins3[3]=(byte) (low_address&0x00ff);
		ins3[2]=(byte)((low_address>>8)&0x00ff);
		
		ins3[5]=(byte) (high_address&0x00ff);
		ins3[4]=(byte)((high_address>>8)&0x00ff);
		//校验CRC的samplecode
				//getCRC
				//hexStr2Byte
				//after should rewrite in one function
				String CRC = CRC16.getCRC(ins3, 6);
				Log.d("sCRC", "the string of crc is"+ CRC);
				byte[] str2byte = CRC16.hexStr2Bytes(CRC);
				ins3[7]= (byte)(str2byte[1]&0x00ff);
				ins3[6]= (byte)(str2byte[0]&0x00ff);
		
		return ins3;
	}
	public byte[] get_mag_power_status4(int low_address, int high_address)
	{
		//分配地址
		ins4[3]=(byte) (low_address&0x00ff);
		ins4[2]=(byte)((low_address>>8)&0x00ff);
		
		ins4[5]=(byte) (high_address&0x00ff);
		ins4[4]=(byte)((high_address>>8)&0x00ff);
		//校验CRC的samplecode
				//getCRC
				//hexStr2Byte
				//after should rewrite in one function
				String CRC = CRC16.getCRC(ins4, 6);
				Log.d("sCRC", "the string of crc is"+ CRC);
				byte[] str2byte = CRC16.hexStr2Bytes(CRC);
				ins4[7]= (byte)(str2byte[1]&0x00ff);
				ins4[6]= (byte)(str2byte[0]&0x00ff);
		
		return ins4;
	}
	public byte[] get_mag_power_status5(int low_address, int high_address)
	{
		//分配地址
		ins5[3]=(byte) (low_address&0x00ff);
		ins5[2]=(byte)((low_address>>8)&0x00ff);
		
		ins5[5]=(byte) (high_address&0x00ff);
		ins5[4]=(byte)((high_address>>8)&0x00ff);
		//校验CRC的samplecode
				//getCRC
				//hexStr2Byte
				//after should rewrite in one function
				String CRC = CRC16.getCRC(ins5, 6);
				Log.d("sCRC", "the string of crc is"+ CRC);
				byte[] str2byte = CRC16.hexStr2Bytes(CRC);
				ins5[7]= (byte)(str2byte[1]&0x00ff);
				ins5[6]= (byte)(str2byte[0]&0x00ff);
		
		return ins5;
	}
	public byte[] get_mag_power_status6(int low_address, int high_address)
	{
		//分配地址
		ins6[3]=(byte) (low_address&0x00ff);
		ins6[2]=(byte)((low_address>>8)&0x00ff);
		
		ins6[5]=(byte) (high_address&0x00ff);
		ins6[4]=(byte)((high_address>>8)&0x00ff);
		//校验CRC的samplecode
				//getCRC
				//hexStr2Byte
				//after should rewrite in one function
				String CRC = CRC16.getCRC(ins6, 6);
				Log.d("sCRC", "the string of crc is"+ CRC);
				byte[] str2byte = CRC16.hexStr2Bytes(CRC);
				ins6[7]= (byte)(str2byte[1]&0x00ff);
				ins6[6]= (byte)(str2byte[0]&0x00ff);
		
		return ins6;
	}
}
