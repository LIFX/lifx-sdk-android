//
//  LxProtocol.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

// Start File: @./LxProtocol.java 

package lifx.java.android.entities.internal.structle;

/*
	**** GENERATED CODE ****
	Start Of File: LxProtocol.java 
*/

import java.util.HashMap;

import lifx.java.android.entities.internal.structle.StructleTypes.Bool8;
import lifx.java.android.entities.internal.structle.StructleTypes.Float32;
import lifx.java.android.entities.internal.structle.StructleTypes.Int16;
import lifx.java.android.entities.internal.structle.StructleTypes.Int32;
import lifx.java.android.entities.internal.structle.StructleTypes.Int64;
import lifx.java.android.entities.internal.structle.StructleTypes.LxProtocolTypeBase;
import lifx.java.android.entities.internal.structle.StructleTypes.ProtocolField;
import lifx.java.android.entities.internal.structle.StructleTypes.RoutingField;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt16;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt32;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt64;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt8;


@SuppressWarnings( "unused")
public class LxProtocol
{
	public enum Type									// Enum Lx::Protocol::Type
	{
  		LX_PROTOCOL_DEVICE_SET_SITE,				// LX_PROTOCOL_DEVICE_SET_SITE = 1
  		LX_PROTOCOL_DEVICE_GET_PAN_GATEWAY,				// LX_PROTOCOL_DEVICE_GET_PAN_GATEWAY = 2
  		LX_PROTOCOL_DEVICE_STATE_PAN_GATEWAY,				// LX_PROTOCOL_DEVICE_STATE_PAN_GATEWAY = 3
  		LX_PROTOCOL_DEVICE_GET_TIME,				// LX_PROTOCOL_DEVICE_GET_TIME = 4
  		LX_PROTOCOL_DEVICE_SET_TIME,				// LX_PROTOCOL_DEVICE_SET_TIME = 5
  		LX_PROTOCOL_DEVICE_STATE_TIME,				// LX_PROTOCOL_DEVICE_STATE_TIME = 6
  		LX_PROTOCOL_DEVICE_GET_RESET_SWITCH,				// LX_PROTOCOL_DEVICE_GET_RESET_SWITCH = 7
  		LX_PROTOCOL_DEVICE_STATE_RESET_SWITCH,				// LX_PROTOCOL_DEVICE_STATE_RESET_SWITCH = 8
  		LX_PROTOCOL_DEVICE_GET_DUMMY_LOAD,				// LX_PROTOCOL_DEVICE_GET_DUMMY_LOAD = 9
  		LX_PROTOCOL_DEVICE_SET_DUMMY_LOAD,				// LX_PROTOCOL_DEVICE_SET_DUMMY_LOAD = 10
  		LX_PROTOCOL_DEVICE_STATE_DUMMY_LOAD,				// LX_PROTOCOL_DEVICE_STATE_DUMMY_LOAD = 11
  		LX_PROTOCOL_DEVICE_GET_MESH_INFO,				// LX_PROTOCOL_DEVICE_GET_MESH_INFO = 12
  		LX_PROTOCOL_DEVICE_STATE_MESH_INFO,				// LX_PROTOCOL_DEVICE_STATE_MESH_INFO = 13
  		LX_PROTOCOL_DEVICE_GET_MESH_FIRMWARE,				// LX_PROTOCOL_DEVICE_GET_MESH_FIRMWARE = 14
  		LX_PROTOCOL_DEVICE_STATE_MESH_FIRMWARE,				// LX_PROTOCOL_DEVICE_STATE_MESH_FIRMWARE = 15
  		LX_PROTOCOL_DEVICE_GET_WIFI_INFO,				// LX_PROTOCOL_DEVICE_GET_WIFI_INFO = 16
  		LX_PROTOCOL_DEVICE_STATE_WIFI_INFO,				// LX_PROTOCOL_DEVICE_STATE_WIFI_INFO = 17
  		LX_PROTOCOL_DEVICE_GET_WIFI_FIRMWARE,				// LX_PROTOCOL_DEVICE_GET_WIFI_FIRMWARE = 18
  		LX_PROTOCOL_DEVICE_STATE_WIFI_FIRMWARE,				// LX_PROTOCOL_DEVICE_STATE_WIFI_FIRMWARE = 19
  		LX_PROTOCOL_DEVICE_GET_POWER,				// LX_PROTOCOL_DEVICE_GET_POWER = 20
  		LX_PROTOCOL_DEVICE_SET_POWER,				// LX_PROTOCOL_DEVICE_SET_POWER = 21
  		LX_PROTOCOL_DEVICE_STATE_POWER,				// LX_PROTOCOL_DEVICE_STATE_POWER = 22
  		LX_PROTOCOL_DEVICE_GET_LABEL,				// LX_PROTOCOL_DEVICE_GET_LABEL = 23
  		LX_PROTOCOL_DEVICE_SET_LABEL,				// LX_PROTOCOL_DEVICE_SET_LABEL = 24
  		LX_PROTOCOL_DEVICE_STATE_LABEL,				// LX_PROTOCOL_DEVICE_STATE_LABEL = 25
  		LX_PROTOCOL_DEVICE_GET_TAGS,				// LX_PROTOCOL_DEVICE_GET_TAGS = 26
  		LX_PROTOCOL_DEVICE_SET_TAGS,				// LX_PROTOCOL_DEVICE_SET_TAGS = 27
  		LX_PROTOCOL_DEVICE_STATE_TAGS,				// LX_PROTOCOL_DEVICE_STATE_TAGS = 28
  		LX_PROTOCOL_DEVICE_GET_TAG_LABELS,			// LX_PROTOCOL_DEVICE_GET_TAG_LABELS = 29
  		LX_PROTOCOL_DEVICE_SET_TAG_LABELS,			// LX_PROTOCOL_DEVICE_SET_TAG_LABELS = 30
  		LX_PROTOCOL_DEVICE_STATE_TAG_LABELS,		// LX_PROTOCOL_DEVICE_STATE_TAG_LABELS = 31
  		LX_PROTOCOL_DEVICE_GET_VERSION,				// LX_PROTOCOL_DEVICE_GET_VERSION = 32
  		LX_PROTOCOL_DEVICE_STATE_VERSION,			// LX_PROTOCOL_DEVICE_STATE_VERSION = 33
  		LX_PROTOCOL_DEVICE_GET_INFO,				// LX_PROTOCOL_DEVICE_GET_INFO = 34
  		LX_PROTOCOL_DEVICE_STATE_INFO,				// LX_PROTOCOL_DEVICE_STATE_INFO = 35
  		LX_PROTOCOL_DEVICE_GET_MCU_RAIL_VOLTAGE,				// LX_PROTOCOL_DEVICE_GET_MCU_RAIL_VOLTAGE = 36
  		LX_PROTOCOL_DEVICE_STATE_MCU_RAIL_VOLTAGE,				// LX_PROTOCOL_DEVICE_STATE_MCU_RAIL_VOLTAGE = 37
  		LX_PROTOCOL_DEVICE_REBOOT,				// LX_PROTOCOL_DEVICE_REBOOT = 38
  		LX_PROTOCOL_DEVICE_SET_FACTORY_TEST_MODE,				// LX_PROTOCOL_DEVICE_SET_FACTORY_TEST_MODE = 39
  		LX_PROTOCOL_DEVICE_DISABLE_FACTORY_TEST_MODE,				// LX_PROTOCOL_DEVICE_DISABLE_FACTORY_TEST_MODE = 40
  		LX_PROTOCOL_DEVICE_STATE_FACTORY_TEST_MODE,				// LX_PROTOCOL_DEVICE_STATE_FACTORY_TEST_MODE = 41
  		LX_PROTOCOL_LIGHT_GET,				// LX_PROTOCOL_LIGHT_GET = 101
  		LX_PROTOCOL_LIGHT_SET,				// LX_PROTOCOL_LIGHT_SET = 102
  		LX_PROTOCOL_LIGHT_SET_WAVEFORM,				// LX_PROTOCOL_LIGHT_SET_WAVEFORM = 103
  		LX_PROTOCOL_LIGHT_SET_DIM_ABSOLUTE,				// LX_PROTOCOL_LIGHT_SET_DIM_ABSOLUTE = 104
  		LX_PROTOCOL_LIGHT_SET_DIM_RELATIVE,				// LX_PROTOCOL_LIGHT_SET_DIM_RELATIVE = 105
  		LX_PROTOCOL_LIGHT_SET_RGBW,				// LX_PROTOCOL_LIGHT_SET_RGBW = 106
  		LX_PROTOCOL_LIGHT_STATE,				// LX_PROTOCOL_LIGHT_STATE = 107
  		LX_PROTOCOL_LIGHT_GET_RAIL_VOLTAGE,				// LX_PROTOCOL_LIGHT_GET_RAIL_VOLTAGE = 108
  		LX_PROTOCOL_LIGHT_STATE_RAIL_VOLTAGE,				// LX_PROTOCOL_LIGHT_STATE_RAIL_VOLTAGE = 109
  		LX_PROTOCOL_LIGHT_GET_TEMPERATURE,				// LX_PROTOCOL_LIGHT_GET_TEMPERATURE = 110
  		LX_PROTOCOL_LIGHT_STATE_TEMPERATURE,				// LX_PROTOCOL_LIGHT_STATE_TEMPERATURE = 111
  		LX_PROTOCOL_LIGHT_SET_CALIBRATION_COEFFICIENTS,				// LX_PROTOCOL_LIGHT_SET_CALIBRATION_COEFFICIENTS = 112
  		LX_PROTOCOL_WAN_CONNECT_PLAIN,				// LX_PROTOCOL_WAN_CONNECT_PLAIN = 201
  		LX_PROTOCOL_WAN_CONNECT_KEY,				// LX_PROTOCOL_WAN_CONNECT_KEY = 202
  		LX_PROTOCOL_WAN_STATE_CONNECT,				// LX_PROTOCOL_WAN_STATE_CONNECT = 203
  		LX_PROTOCOL_WAN_SUB,				// LX_PROTOCOL_WAN_SUB = 204
  		LX_PROTOCOL_WAN_UNSUB,				// LX_PROTOCOL_WAN_UNSUB = 205
  		LX_PROTOCOL_WAN_STATE_SUB,				// LX_PROTOCOL_WAN_STATE_SUB = 206
  		LX_PROTOCOL_WIFI_GET,				// LX_PROTOCOL_WIFI_GET = 301
  		LX_PROTOCOL_WIFI_SET,				// LX_PROTOCOL_WIFI_SET = 302
  		LX_PROTOCOL_WIFI_STATE,				// LX_PROTOCOL_WIFI_STATE = 303
  		LX_PROTOCOL_WIFI_GET_ACCESS_POINT,				// LX_PROTOCOL_WIFI_GET_ACCESS_POINT = 304
  		LX_PROTOCOL_WIFI_SET_ACCESS_POINT,				// LX_PROTOCOL_WIFI_SET_ACCESS_POINT = 305
  		LX_PROTOCOL_WIFI_STATE_ACCESS_POINT,				// LX_PROTOCOL_WIFI_STATE_ACCESS_POINT = 306
  		LX_PROTOCOL_SENSOR_GET_AMBIENT_LIGHT,				// LX_PROTOCOL_SENSOR_GET_AMBIENT_LIGHT = 401
  		LX_PROTOCOL_SENSOR_STATE_AMBIENT_LIGHT,				// LX_PROTOCOL_SENSOR_STATE_AMBIENT_LIGHT = 402
  		LX_PROTOCOL_SENSOR_GET_DIMMER_VOLTAGE,				// LX_PROTOCOL_SENSOR_GET_DIMMER_VOLTAGE = 403
  		LX_PROTOCOL_SENSOR_STATE_DIMMER_VOLTAGE,				// LX_PROTOCOL_SENSOR_STATE_DIMMER_VOLTAGE = 404
	};
	
	public static HashMap<Type,Integer> typeValueMap;
	public static HashMap<Integer,Type> typeMap;
    	public static HashMap<Type,Class<? extends LxProtocolTypeBase>> typeClassMap;
	

  	static
  	{
  		typeValueMap = new HashMap<Type,Integer>();
  		typeMap = new HashMap<Integer,Type>();
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_SET_SITE, 1);
		typeMap.put( 1, Type.LX_PROTOCOL_DEVICE_SET_SITE);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_PAN_GATEWAY, 2);
		typeMap.put( 2, Type.LX_PROTOCOL_DEVICE_GET_PAN_GATEWAY);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_PAN_GATEWAY, 3);
		typeMap.put( 3, Type.LX_PROTOCOL_DEVICE_STATE_PAN_GATEWAY);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_TIME, 4);
		typeMap.put( 4, Type.LX_PROTOCOL_DEVICE_GET_TIME);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_SET_TIME, 5);
		typeMap.put( 5, Type.LX_PROTOCOL_DEVICE_SET_TIME);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_TIME, 6);
		typeMap.put( 6, Type.LX_PROTOCOL_DEVICE_STATE_TIME);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_RESET_SWITCH, 7);
		typeMap.put( 7, Type.LX_PROTOCOL_DEVICE_GET_RESET_SWITCH);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_RESET_SWITCH, 8);
		typeMap.put( 8, Type.LX_PROTOCOL_DEVICE_STATE_RESET_SWITCH);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_DUMMY_LOAD, 9);
		typeMap.put( 9, Type.LX_PROTOCOL_DEVICE_GET_DUMMY_LOAD);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_SET_DUMMY_LOAD, 10);
		typeMap.put( 10, Type.LX_PROTOCOL_DEVICE_SET_DUMMY_LOAD);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_DUMMY_LOAD, 11);
		typeMap.put( 11, Type.LX_PROTOCOL_DEVICE_STATE_DUMMY_LOAD);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_MESH_INFO, 12);
		typeMap.put( 12, Type.LX_PROTOCOL_DEVICE_GET_MESH_INFO);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_MESH_INFO, 13);
		typeMap.put( 13, Type.LX_PROTOCOL_DEVICE_STATE_MESH_INFO);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_MESH_FIRMWARE, 14);
		typeMap.put( 14, Type.LX_PROTOCOL_DEVICE_GET_MESH_FIRMWARE);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_MESH_FIRMWARE, 15);
		typeMap.put( 15, Type.LX_PROTOCOL_DEVICE_STATE_MESH_FIRMWARE);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_WIFI_INFO, 16);
		typeMap.put( 16, Type.LX_PROTOCOL_DEVICE_GET_WIFI_INFO);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_WIFI_INFO, 17);
		typeMap.put( 17, Type.LX_PROTOCOL_DEVICE_STATE_WIFI_INFO);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_WIFI_FIRMWARE, 18);
		typeMap.put( 18, Type.LX_PROTOCOL_DEVICE_GET_WIFI_FIRMWARE);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_WIFI_FIRMWARE, 19);
		typeMap.put( 19, Type.LX_PROTOCOL_DEVICE_STATE_WIFI_FIRMWARE);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_POWER, 20);
		typeMap.put( 20, Type.LX_PROTOCOL_DEVICE_GET_POWER);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_SET_POWER, 21);
		typeMap.put( 21, Type.LX_PROTOCOL_DEVICE_SET_POWER);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_POWER, 22);
		typeMap.put( 22, Type.LX_PROTOCOL_DEVICE_STATE_POWER);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_LABEL, 23);
		typeMap.put( 23, Type.LX_PROTOCOL_DEVICE_GET_LABEL);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_SET_LABEL, 24);
		typeMap.put( 24, Type.LX_PROTOCOL_DEVICE_SET_LABEL);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_LABEL, 25);
		typeMap.put( 25, Type.LX_PROTOCOL_DEVICE_STATE_LABEL);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_TAGS, 26);
		typeMap.put( 26, Type.LX_PROTOCOL_DEVICE_GET_TAGS);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_SET_TAGS, 27);
		typeMap.put( 27, Type.LX_PROTOCOL_DEVICE_SET_TAGS);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_TAGS, 28);
		typeMap.put( 28, Type.LX_PROTOCOL_DEVICE_STATE_TAGS);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_TAG_LABELS, 29);
		typeMap.put( 29, Type.LX_PROTOCOL_DEVICE_GET_TAG_LABELS);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_SET_TAG_LABELS, 30);
		typeMap.put( 30, Type.LX_PROTOCOL_DEVICE_SET_TAG_LABELS);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_TAG_LABELS, 31);
		typeMap.put( 31, Type.LX_PROTOCOL_DEVICE_STATE_TAG_LABELS);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_VERSION, 32);
		typeMap.put( 32, Type.LX_PROTOCOL_DEVICE_GET_VERSION);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_VERSION, 33);
		typeMap.put( 33, Type.LX_PROTOCOL_DEVICE_STATE_VERSION);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_INFO, 34);
		typeMap.put( 34, Type.LX_PROTOCOL_DEVICE_GET_INFO);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_INFO, 35);
		typeMap.put( 35, Type.LX_PROTOCOL_DEVICE_STATE_INFO);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_GET_MCU_RAIL_VOLTAGE, 36);
		typeMap.put( 36, Type.LX_PROTOCOL_DEVICE_GET_MCU_RAIL_VOLTAGE);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_MCU_RAIL_VOLTAGE, 37);
		typeMap.put( 37, Type.LX_PROTOCOL_DEVICE_STATE_MCU_RAIL_VOLTAGE);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_REBOOT, 38);
		typeMap.put( 38, Type.LX_PROTOCOL_DEVICE_REBOOT);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_SET_FACTORY_TEST_MODE, 39);
		typeMap.put( 39, Type.LX_PROTOCOL_DEVICE_SET_FACTORY_TEST_MODE);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_DISABLE_FACTORY_TEST_MODE, 40);
		typeMap.put( 40, Type.LX_PROTOCOL_DEVICE_DISABLE_FACTORY_TEST_MODE);
		typeValueMap.put( Type.LX_PROTOCOL_DEVICE_STATE_FACTORY_TEST_MODE, 41);
		typeMap.put( 41, Type.LX_PROTOCOL_DEVICE_STATE_FACTORY_TEST_MODE);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_GET, 101);
		typeMap.put( 101, Type.LX_PROTOCOL_LIGHT_GET);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_SET, 102);
		typeMap.put( 102, Type.LX_PROTOCOL_LIGHT_SET);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_SET_WAVEFORM, 103);
		typeMap.put( 103, Type.LX_PROTOCOL_LIGHT_SET_WAVEFORM);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_SET_DIM_ABSOLUTE, 104);
		typeMap.put( 104, Type.LX_PROTOCOL_LIGHT_SET_DIM_ABSOLUTE);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_SET_DIM_RELATIVE, 105);
		typeMap.put( 105, Type.LX_PROTOCOL_LIGHT_SET_DIM_RELATIVE);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_SET_RGBW, 106);
		typeMap.put( 106, Type.LX_PROTOCOL_LIGHT_SET_RGBW);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_STATE, 107);
		typeMap.put( 107, Type.LX_PROTOCOL_LIGHT_STATE);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_GET_RAIL_VOLTAGE, 108);
		typeMap.put( 108, Type.LX_PROTOCOL_LIGHT_GET_RAIL_VOLTAGE);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_STATE_RAIL_VOLTAGE, 109);
		typeMap.put( 109, Type.LX_PROTOCOL_LIGHT_STATE_RAIL_VOLTAGE);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_GET_TEMPERATURE, 110);
		typeMap.put( 110, Type.LX_PROTOCOL_LIGHT_GET_TEMPERATURE);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_STATE_TEMPERATURE, 111);
		typeMap.put( 111, Type.LX_PROTOCOL_LIGHT_STATE_TEMPERATURE);
		typeValueMap.put( Type.LX_PROTOCOL_LIGHT_SET_CALIBRATION_COEFFICIENTS, 112);
		typeMap.put( 112, Type.LX_PROTOCOL_LIGHT_SET_CALIBRATION_COEFFICIENTS);
		typeValueMap.put( Type.LX_PROTOCOL_WAN_CONNECT_PLAIN, 201);
		typeMap.put( 201, Type.LX_PROTOCOL_WAN_CONNECT_PLAIN);
		typeValueMap.put( Type.LX_PROTOCOL_WAN_CONNECT_KEY, 202);
		typeMap.put( 202, Type.LX_PROTOCOL_WAN_CONNECT_KEY);
		typeValueMap.put( Type.LX_PROTOCOL_WAN_STATE_CONNECT, 203);
		typeMap.put( 203, Type.LX_PROTOCOL_WAN_STATE_CONNECT);
		typeValueMap.put( Type.LX_PROTOCOL_WAN_SUB, 204);
		typeMap.put( 204, Type.LX_PROTOCOL_WAN_SUB);
		typeValueMap.put( Type.LX_PROTOCOL_WAN_UNSUB, 205);
		typeMap.put( 205, Type.LX_PROTOCOL_WAN_UNSUB);
		typeValueMap.put( Type.LX_PROTOCOL_WAN_STATE_SUB, 206);
		typeMap.put( 206, Type.LX_PROTOCOL_WAN_STATE_SUB);
		typeValueMap.put( Type.LX_PROTOCOL_WIFI_GET, 301);
		typeMap.put( 301, Type.LX_PROTOCOL_WIFI_GET);
		typeValueMap.put( Type.LX_PROTOCOL_WIFI_SET, 302);
		typeMap.put( 302, Type.LX_PROTOCOL_WIFI_SET);
		typeValueMap.put( Type.LX_PROTOCOL_WIFI_STATE, 303);
		typeMap.put( 303, Type.LX_PROTOCOL_WIFI_STATE);
		typeValueMap.put( Type.LX_PROTOCOL_WIFI_GET_ACCESS_POINT, 304);
		typeMap.put( 304, Type.LX_PROTOCOL_WIFI_GET_ACCESS_POINT);
		typeValueMap.put( Type.LX_PROTOCOL_WIFI_SET_ACCESS_POINT, 305);
		typeMap.put( 305, Type.LX_PROTOCOL_WIFI_SET_ACCESS_POINT);
		typeValueMap.put( Type.LX_PROTOCOL_WIFI_STATE_ACCESS_POINT, 306);
		typeMap.put( 306, Type.LX_PROTOCOL_WIFI_STATE_ACCESS_POINT);
		typeValueMap.put( Type.LX_PROTOCOL_SENSOR_GET_AMBIENT_LIGHT, 401);
		typeMap.put( 401, Type.LX_PROTOCOL_SENSOR_GET_AMBIENT_LIGHT);
		typeValueMap.put( Type.LX_PROTOCOL_SENSOR_STATE_AMBIENT_LIGHT, 402);
		typeMap.put( 402, Type.LX_PROTOCOL_SENSOR_STATE_AMBIENT_LIGHT);
		typeValueMap.put( Type.LX_PROTOCOL_SENSOR_GET_DIMMER_VOLTAGE, 403);
		typeMap.put( 403, Type.LX_PROTOCOL_SENSOR_GET_DIMMER_VOLTAGE);
		typeValueMap.put( Type.LX_PROTOCOL_SENSOR_STATE_DIMMER_VOLTAGE, 404);
		typeMap.put( 404, Type.LX_PROTOCOL_SENSOR_STATE_DIMMER_VOLTAGE);
  
  		typeClassMap = new HashMap<Type,Class<? extends LxProtocolTypeBase>>();
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_SET_SITE, LxProtocolDevice.SetSite.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_PAN_GATEWAY, LxProtocolDevice.GetPanGateway.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_PAN_GATEWAY, LxProtocolDevice.StatePanGateway.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_TIME, LxProtocolDevice.GetTime.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_SET_TIME, LxProtocolDevice.SetTime.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_TIME, LxProtocolDevice.StateTime.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_RESET_SWITCH, LxProtocolDevice.GetResetSwitch.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_RESET_SWITCH, LxProtocolDevice.StateResetSwitch.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_DUMMY_LOAD, LxProtocolDevice.GetDummyLoad.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_SET_DUMMY_LOAD, LxProtocolDevice.SetDummyLoad.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_DUMMY_LOAD, LxProtocolDevice.StateDummyLoad.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_MESH_INFO, LxProtocolDevice.GetMeshInfo.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_MESH_INFO, LxProtocolDevice.StateMeshInfo.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_MESH_FIRMWARE, LxProtocolDevice.GetMeshFirmware.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_MESH_FIRMWARE, LxProtocolDevice.StateMeshFirmware.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_WIFI_INFO, LxProtocolDevice.GetWifiInfo.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_WIFI_INFO, LxProtocolDevice.StateWifiInfo.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_WIFI_FIRMWARE, LxProtocolDevice.GetWifiFirmware.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_WIFI_FIRMWARE, LxProtocolDevice.StateWifiFirmware.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_POWER, LxProtocolDevice.GetPower.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_SET_POWER, LxProtocolDevice.SetPower.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_POWER, LxProtocolDevice.StatePower.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_LABEL, LxProtocolDevice.GetLabel.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_SET_LABEL, LxProtocolDevice.SetLabel.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_LABEL, LxProtocolDevice.StateLabel.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_TAGS, LxProtocolDevice.GetTags.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_SET_TAGS, LxProtocolDevice.SetTags.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_TAGS, LxProtocolDevice.StateTags.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_TAG_LABELS, LxProtocolDevice.GetTagLabels.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_SET_TAG_LABELS, LxProtocolDevice.SetTagLabels.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_TAG_LABELS, LxProtocolDevice.StateTagLabels.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_VERSION, LxProtocolDevice.GetVersion.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_VERSION, LxProtocolDevice.StateVersion.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_INFO, LxProtocolDevice.GetInfo.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_INFO, LxProtocolDevice.StateInfo.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_GET_MCU_RAIL_VOLTAGE, LxProtocolDevice.GetMcuRailVoltage.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_MCU_RAIL_VOLTAGE, LxProtocolDevice.StateMcuRailVoltage.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_REBOOT, LxProtocolDevice.Reboot.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_SET_FACTORY_TEST_MODE, LxProtocolDevice.SetFactoryTestMode.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_DISABLE_FACTORY_TEST_MODE, LxProtocolDevice.DisableFactoryTestMode.class);
        		typeClassMap.put( Type.LX_PROTOCOL_DEVICE_STATE_FACTORY_TEST_MODE, LxProtocolDevice.StateFactoryTestMode.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_GET, LxProtocolLight.Get.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_SET, LxProtocolLight.Set.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_SET_WAVEFORM, LxProtocolLight.SetWaveform.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_SET_DIM_ABSOLUTE, LxProtocolLight.SetDimAbsolute.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_SET_DIM_RELATIVE, LxProtocolLight.SetDimRelative.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_SET_RGBW, LxProtocolLight.SetRgbw.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_STATE, LxProtocolLight.State.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_GET_RAIL_VOLTAGE, LxProtocolLight.GetRailVoltage.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_STATE_RAIL_VOLTAGE, LxProtocolLight.StateRailVoltage.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_GET_TEMPERATURE, LxProtocolLight.GetTemperature.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_STATE_TEMPERATURE, LxProtocolLight.StateTemperature.class);
        		typeClassMap.put( Type.LX_PROTOCOL_LIGHT_SET_CALIBRATION_COEFFICIENTS, LxProtocolLight.SetCalibrationCoefficients.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WAN_CONNECT_PLAIN, LxProtocolWan.ConnectPlain.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WAN_CONNECT_KEY, LxProtocolWan.ConnectKey.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WAN_STATE_CONNECT, LxProtocolWan.StateConnect.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WAN_SUB, LxProtocolWan.Sub.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WAN_UNSUB, LxProtocolWan.Unsub.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WAN_STATE_SUB, LxProtocolWan.StateSub.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WIFI_GET, LxProtocolWifi.Get.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WIFI_SET, LxProtocolWifi.Set.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WIFI_STATE, LxProtocolWifi.State.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WIFI_GET_ACCESS_POINT, LxProtocolWifi.GetAccessPoint.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WIFI_SET_ACCESS_POINT, LxProtocolWifi.SetAccessPoint.class);
        		typeClassMap.put( Type.LX_PROTOCOL_WIFI_STATE_ACCESS_POINT, LxProtocolWifi.StateAccessPoint.class);
        		typeClassMap.put( Type.LX_PROTOCOL_SENSOR_GET_AMBIENT_LIGHT, LxProtocolSensor.GetAmbientLight.class);
        		typeClassMap.put( Type.LX_PROTOCOL_SENSOR_STATE_AMBIENT_LIGHT, LxProtocolSensor.StateAmbientLight.class);
        		typeClassMap.put( Type.LX_PROTOCOL_SENSOR_GET_DIMMER_VOLTAGE, LxProtocolSensor.GetDimmerVoltage.class);
        		typeClassMap.put( Type.LX_PROTOCOL_SENSOR_STATE_DIMMER_VOLTAGE, LxProtocolSensor.StateDimmerVoltage.class);
  	};
	
}

/*
	End Of File: LxProtocol.java
*/

