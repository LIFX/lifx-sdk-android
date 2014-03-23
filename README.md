# LIFX Android SDK - the LIFX SDK for Android

The SDK currently supports Android only. Java support is coming soon.

### Installation

#### Library Project Installation
The LIFX SDK will be published as a JAR archive when it is released publicly, but until then you can still use the SDK by cloning the repository and importing it into your project as an Android Library Project.

#### Eclipse Sub-Project Installation
1. Clone the SDK repository into a directory on your local machine.
2. Open Eclipse and choose to import a "existing android code"
3. Import the library project.
4. Open the project properties for your application, select android then add the library project.

### Quick Examples

Turn off all lights:
```java
LFXNetworkContext localNetworkContext = LFXClient.getSharedInstance( this).getLocalNetworkContext();
localNetworkContext.getAllLightsCollection().setPowerState( LFXPowerState.OFF);
```

Turn on the light named "Hallway":
```java
LFXNetworkContext localNetworkContext = LFXClient.getSharedInstance( this).getLocalNetworkContext();
LFXLight hallway = localNetworkContext.getAllLightsCollection().getFirstLightForLabel( "Hallway");
hallway.setPowerState( LFXPowerState.ON);
```

Turn on all lights that are tagged "Kitchen":
```java
LFXNetworkContext localNetworkContext = LFXClient.getSharedInstance( this).getLocalNetworkContext();
LFXTaggedLightCollection kitchen = localNetworkContext.getTaggedLightCollectionForTag( "Kitchen");
kitchen.setPowerState( LFXPowerState.ON);
```

Set every light to a random color:
```java
LFXNetworkContext localNetworkContext = LFXClient.getSharedInstance( this).getLocalNetworkContext();

for( LFXLight aLight : localNetworkContext.getAllLightsCollection().getLights())
{
	LFXHSBKColor color = LFXHSBKColor.getColor( (float)(Math.random() * 360), 1.0f, 1.0f, 3500);
	aLight.setColor( color);
}
```

---------------

## Quick overview of LIFX SDK terminology

### Network Context

A Network Context refers to a context within which you will be accessing LIFX devices. The SDK currently only has support for a "Local Network Context", which refers to devices accessible on your LAN. We plan on adding Network Contexts for accessing your LIFX devices via our cloud service.

### Tags and Addressing

At the binary protocol level, we have three types of addressing - device, tag and broadcast. The details aren't important for SDK users, but it is important to be aware of the implications on performance.

Due to the low bandwidth available on the LIFX mesh radio system, performance is much better when sending a single message to a tag instead of sending individual messages to every device within a tag. Tags represent user-configured groupings, e.g. "Kitchen", "Hallway", "Bedroom".

### Light Collections

Light Collections are classes that encapsulate a group of LIFX lights. They can have their light state manipulated in the same way that an individual light can. This is not only for developer convenience when writing UI code, it's important for getting the best performance out of a LIFX network.

### Tagged Light Collections

A Tagged Light Collection represents the lights contained within a particular tag. If you want to manipulate every device within a tag, you should always use the Tagged Light Collection instead of dealing with each Light individually. You'll see much faster performance that way.

### HSBK Color

LIFX makes use of a "HSBK" color representation. The "HSB" part refers to the [HSB](http://en.wikipedia.org/wiki/HSB_color_space) color space, and the "K" part refers to the [Color Temperature](http://en.wikipedia.org/wiki/Color_temperature) of the white point, in Kelvin. At full saturation, HSBK colors will correspond to the edge of the realisable color gamut, and at zero saturation, HSBK colors will correspond to the color described by the Color Temperature in the K component.

### LIFX Service Discovery

The LIFX SDK will need to discover devices on your network before it can talk to them. Due to the asynchronous nature of both the LIFX system, and the client SDKs, you won't be able to list all of the devices straight away, you'll have to check to see what devices have been found at a given point in time.

