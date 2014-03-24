# LIFX Android SDK - the LIFX SDK for Android

The SDK currently supports Android only. Java support is coming soon.

### Installation

#### Library Project Installation
The LIFX SDK will be published as a JAR archive when it is released publicly, but until then you can still use the SDK by cloning the repository and importing it into your project as an Android Library Project.
Users should note that the library is currently in an early Beta stage, so the API itself could change in the future.

#### Eclipse Sub-Project Installation
1. Clone the SDK repository into a directory on your local machine.
2. Open Eclipse and choose to import a "existing android code"
3. Import the library project.
4. Open the project properties for your application, select android then add the library project.

### Quick Examples

Initializing the library:

Before using the Library functions, a connection must be established with the LIFX lights that are on the current network.
First a reference to local network is gathered, then a connection is requested.
```java
LFXNetworkContext localNetworkContext = LFXClient.getSharedInstance( this).getLocalNetworkContext();
localNetworkContext.connect();
```

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

Disconnect:

When the application is finished with the LIFX bulbs, you will need to signal the Library to end it's connections.
```java
LFXNetworkContext localNetworkContext = LFXClient.getSharedInstance( this).getLocalNetworkContext();
localNetworkContext.disconnect();
```

---------------

## Quick overview of LIFX SDK terminology

### Network Context

A Network Context (`LFXNetworkContext`) denotes a context within which you will be accessing LIFX devices. The SDK currently only has support for a "Local Network Context", which refers to devices accessible on your LAN. We plan on adding Network Contexts for accessing LIFX devices via the upcoming cloud service.

### Lights, Light Collections and Addressing

The underlying LIFX system has three types of internal address: device, tag and broadcast. Device addressing will target an individual device, tag addressing will target all devices that have a particular tag, and broadcast addressing addressing will target all devices. Due to the way the underlying LIFX Binary Protocol works, you'll see much faster responses by targetting a tag (using a Tagged Light Collection) rather than targetting every device within that tag individually. The same goes for targetting all lights - if you want to change the state on all lights, you'll see much faster performance targetting `-[LFXNetworkContext allLightsCollection]` instead of targetting each device individually.

### Lights

A `LFXLight` object represents an individual LIFX Light, and there will be only one `LFXLight` corresponding to each device per Network Context.

### Light Collections

Light Collections (`LFXLightCollection`) are classes that encapsulate a group of LIFX lights. They can have their light state manipulated in the same way that an individual light can. You can access the lights within a Light Collection through the `.lights` property.

### The All Lights Collection

Each `LFXNetworkContext` has an `.allLightsCollection` property, which is how you get the list of lights. If you want to manipulate every light in the same way (e.g. to turn every light off), you should use the All Lights Collection property directly, instead of targetting each device individually.

### Tagged Light Collections

A Tagged Light Collection (`LFXTaggedLightCollection`) represents the lights contained within a particular tag. If you want to manipulate every device within a tag, you should always use the Tagged Light Collection instead of dealing with each Light individually. Tagged Light Collections are unique within a Network Context.

### HSBK Color

LIFX makes use of a "HSBK" color representation. The "HSB" part refers to the [HSB](http://en.wikipedia.org/wiki/HSB_color_space) color space, and the "K" part refers to the [Color Temperature](http://en.wikipedia.org/wiki/Color_temperature) of the white point, in Kelvin. At full saturation, HSBK colors will correspond to the edge of the realisable color gamut, and at zero saturation, HSBK colors will correspond to the color described by the Color Temperature in the K component.
