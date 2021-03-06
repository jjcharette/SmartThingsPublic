preferences {
        input("ip", "string", title:"IP Address", description: "10.0.0.10", required: true, displayDuringSetup: false)
        input("port", "string", title:"Port", description: "80", defaultValue: 80 , required: true, displayDuringSetup: false)
        input("unit", "string", title:"Unit", description: "C", defaultValue: "C" , displayDuringSetup: false)
        //new
        input "outlets", "capability.switch", title: "Outlets", multiple: true, required: false
        input "sensor", "capability.temperatureMeasurement", title: "Sensor", multiple: false, required: false
        input "sensor2", "capability.temperatureMeasurement", title: "Sensor 2", multiple: false, required: false
        input "sensor3", "capability.temperatureMeasurement", title: "Sensor 3 ", multiple: false, required: false
        input "sensor4", "capability.temperatureMeasurement", title: "Sensor 4", multiple: false, required: false
        input "sensorArr", "capability.temperatureMeasurement", title: "SensorArray", multiple: true, required: false
        input "motion", "capability.motionSensor", title: "Motion", required: false, multiple: true
}
metadata {
	definition (name: "x10 Thermostat v2", namespace: "daggasoft", author: "daggasoft") {
		capability "Actuator"
		capability "Polling"
		capability "Presence Sensor"
		capability "Relative Humidity Measurement"
		capability "Sensor"
		capability "Temperature Measurement"
		capability "Thermostat"

		command "away"
		command "present"
		command "setPresence"
		command "heatingSetpointUp"
		command "heatingSetpointDown"
		command "coolingSetpointUp"
		command "coolingSetpointDown"
        command "fireplaceOff"
        command "fireplaceOn"
		command "setFahrenheit"
		command "setCelsius"
        command "setTemperature", ["number"]

        //depricated
        command "tempUp"
        command "tempDown"

        attribute "temperatureUnit", "string"
	}
  tiles(scale: 2) {
  standardTile("tempreadout", "device.temperature", width: 6, height: 2, canChangeIcon: false) { //main things list test switch, set in main
      state "default", label:'${currentValue}°', unit:"C",  action: "switch.off", icon: "st.Weather.weather2", backgroundColor: "#80dfff"
      //state "default", label:'24°', unit:"dC",  action: "switch.off", icon: "st.Weather.weather2", backgroundColor: "#80dfff"
      
      //doesnt work
      state "off", label:'24°', unit:"dC",  action: "switch.auto", icon: "st.thermostat.heating-cooling-off", backgroundColor: "#ffffff"
      state "auto", label:'24°', unit:"dC",  action: "switch.cool", icon: "st.thermostat.heating-cooling-off", backgroundColor: "#79b821"
      state "cool", label:'24°', unit:"dC",  action: "switch.heat", icon: "st.thermostat.cool", backgroundColor: "#0099ff"
      state "heat", label:'24°', unit:"dC",  action: "switch.off", icon: "st.thermostat.heat", backgroundColor: "#ffa81e"

  }
	multiAttributeTile(name:"temperature", type:"thermostat", width: 2, height: 4) {
		tileAttribute("device.temperature", key: "PRIMARY_CONTROL") {
	  attributeState("default", label:'${currentValue}°', /*icon:"st.Home.home1", */backgroundColors:[
      //attributeState("default", label:'24°', /*icon:"st.Home.home1", */backgroundColors:[
          // Celsius Color Range
          [value: 0, color: "#153591"],
          [value: 7, color: "#1e9cbb"],
          [value: 15, color: "#90d2a7"],
          [value: 23, color: "#44b621"],
          [value: 29, color: "#f1d801"],
          [value: 33, color: "#d04e00"],
          [value: 36, color: "#bc2323"],
          // Fahrenheit Color Range
          [value: 40, color: "#153591"],
          [value: 44, color: "#1e9cbb"],
          [value: 59, color: "#90d2a7"],
          [value: 74, color: "#44b621"],
          [value: 84, color: "#f1d801"],
          [value: 92, color: "#d04e00"],
          [value: 96, color: "#ff8426"]
			]
			)
		}
		/*tileAttribute("device.humidity", key: "SECONDARY_CONTROL") {
			attributeState("level", label:'32%')
            //attributeState("level", label:'${currentValue}%')
		}*/
        tileAttribute("device.coolingSetpoint", key: "SECONDARY_CONTROL") {
			attributeState("level", label:'${currentValue}°', icon:"st.Weather.weather2")
		}
        tileAttribute("device.temperature", key: "VALUE_CONTROL") {
        	attributeState("VALUE_UP", action: "coolingSetpointUp")
        	attributeState("VALUE_DOWN", action: "coolingSetpointDown")
    	}
		tileAttribute("device.thermostatOperatingState", key: "OPERATING_STATE") {
			//attributeState("idle", backgroundColor:"#44b621")
            attributeState("idle", backgroundColor:"#b5b5b5")         
			attributeState("heating", backgroundColor:"#ffa81e")
			attributeState("cooling", backgroundColor:"#269bd2")
		}
        /*tileAttribute("device.thermostatMode", key: "THERMOSTAT_MODE") {
			attributeState("off", label:'${name}')
			attributeState("heat", label:'${name}')
			attributeState("cool", label:'${name}')
			attributeState("auto", label:'${name}')
            attributeState("idle", label:'${name}', backgroundColor:"#44b621")
      		//attributeState("timer", label:'${name}')
		}*/
		tileAttribute("device.thermostatOperatingState", key: "THERMOSTAT_MODE") {
			attributeState("idle", backgroundColor:"#44b621")
			attributeState("heating", backgroundColor:"#ffa81e")
			attributeState("cooling", backgroundColor:"#269bd2")
            attributeState("off", label:'${name}')
            attributeState("auto", label:'${name}')
		}
        
       // tileAttribute("device.fireplaceState", key: "FIREPLACE_MODE") {
		//	attributeState("off", label:'${name}')
		//	attributeState("on", label:'${name}', backgroundColor:"#ffa81e")
		//}
		tileAttribute("device.heatingSetpoint", key: "HEATING_SETPOINT") {
			attributeState("default", label:'19')
            //attributeState("default", label:'${currentValue}')
		}
		tileAttribute("device.coolingSetpoint", key: "COOLING_SETPOINT") {
			//attributeState("default", label:'${currentValue}')
            attributeState("default", label:'25')
		}
	}

	standardTile("heatingSetpointUp", "device.heatingSetpoint", decoration: "flat", width: 1, height: 1) {
		state "heatingSetpointUp", label:' Heat ', action:"heatingSetpointUp", icon:"st.thermostat.thermostat-up"
	}

	standardTile("coolingSetpointUp", "device.coolingSetpoint", decoration: "flat", width: 1, height: 1) {
		state "coolingSetpointUp", action:"coolingSetpointUp", icon:"st.thermostat.thermostat-up"
	}

	valueTile("heatingSetpoint", "device.heatingSetpoint", width: 2, height: 2) {
		state "default", label:'${currentValue}°', unit:"Heat", backgroundColor:"#ff8426"
	}

	valueTile("coolingSetpoint", "device.coolingSetpoint", width: 2, height: 2) {
		state "default", label:'${currentValue}°', unit:"Cool", backgroundColor:"#1e9cbb"
	}
    
 /*  	standardTile("temperature1", "device.temperature1", decoration: "flat", width: 3, height: 2) {
		state "default",  label: 'Hallway (${currentValue}°)', icon: "st.Weather.weather2", action:"setCelsius"
		state "celsius", label: "°C", icon: "st.Weather.weather2", action:"setFahrenheit"
	}
*/   
    valueTile("temperature1", "device.temperature1", decoration: "flat", width: 3, height: 1) {
		state "default", label:'Hallway (${currentValue}°)', icon: "st.Weather.weather2", unit:"C"
	}
    
	valueTile("temperature2", "device.temperature2", width: 3, height: 1) {
		state "default", label:'Kitchen (${currentValue}°)', icon: "st.Weather.weather2", unit:"C"
	}
	valueTile("temperature3", "device.temperature3", width: 3, height: 1) {
		state "default", label:'Living Room (${currentValue}°)', icon: "st.Weather.weather2", unit:"C"
	}
	valueTile("temperature4", "device.temperature4", width: 3, height: 1) {
		state "default", label:'Upstairs (${currentValue}°)', icon: "st.Weather.weather2", unit:"C"
	}
    valueTile("tempmin", "device.tempmin", width: 1, height: 1) {
		state "default", label:'MIN       ${currentValue}°', icon: "st.Weather.weather2", unit:"C"
	}
	valueTile("tempmax", "device.tempmax", width: 1, height: 1) {
		state "default", label:'MAX       ${currentValue}°', icon: "st.Weather.weather2", unit:"C"
	}
	valueTile("tempavg", "device.tempavg", width: 1, height: 1) {
		state "default", label:'AVG       ${currentValue}°', icon: "st.Weather.weather2", unit:"C"
	}
    
	standardTile("heatingSetpointDown", "device.heatingSetpoint", decoration: "flat", width: 1, height: 1) {
		state "heatingSetpointDown", label:' Heat ', action:"heatingSetpointDown", icon:"st.thermostat.thermostat-down"
	}

	standardTile("coolingSetpointDown", "device.coolingSetpoint", decoration: "flat", width: 1, height: 1) {
		state "coolingSetpointDown", action:"coolingSetpointDown", icon:"st.thermostat.thermostat-down"
	}

	standardTile("thermostatMode", "device.thermostatMode", inactiveLabel: true, decoration: "flat", width: 2, height: 2) {
		state("auto", action:"thermostat.off", icon: "st.thermostat.auto")
		state("off", action:"thermostat.cool", icon: "st.thermostat.heating-cooling-off")
		state("cool", action:"thermostat.heat", icon: "st.thermostat.cool")
		state("heat", action:"thermostat.auto", icon: "st.thermostat.heat")
    //state("heat", action:"thermostat.timer", icon: "st.thermostat.heat")
    //state("timer", action:"thermostat.auto", icon: "st.Office.office6")

	}
    
    standardTile("blank", " ", inactiveLabel: true, decoration: "flat", width: 1, height: 1) {
		state "defult"
	}

	standardTile("thermostatFanMode", "device.thermostatFanMode", inactiveLabel: true, decoration: "flat", width: 2, height: 1) {
		state "auto", action:"thermostat.fanOn", icon: "st.thermostat.fan-auto"
		state "on", action:"thermostat.fanCirculate", icon: "st.thermostat.fan-on"
		state "circulate", action:"thermostat.fanAuto", icon: "st.thermostat.fan-circulate"
	}

	standardTile("fireplace", "device.fireplace", decoration: "flat", width: 2, height: 2) {
		state "on", label: "On", action:"fireplaceOff", icon: "st.Home.home29", backgroundColor:"#ff8426"
        state "off", label: "Off", action:"fireplaceOn", icon: "st.Home.home29"
	}

	standardTile("presence", "device.presence", decoration: "flat", width: 6, height: 2) {
		state "present", label:'${name}', action:"away", icon: "st.Home.home2"
		state "not present", label:'away', action:"present", icon: "st.Transportation.transportation5"
	}

	standardTile("refresh", "device.thermostatMode", decoration: "flat", width: 1, height: 1) {
		state "default", action:"polling.poll", icon:"st.secondary.refresh"
	}

	standardTile("thermostatOperatingState", "device.thermostatOperatingState", decoration: "flat", width: 2, height: 2) {
		state "idle", action:"polling.poll", label:'${name}', icon: 'http://cdn.device-icons.smartthings.com/sonos/pause-icon@2x.png'
		state "cooling", action:"polling.poll", label:' ', icon: "st.thermostat.cooling"
		state "heating", action:"polling.poll", label:' ', icon: "st.thermostat.heating"
		state "fan only", action:"polling.poll", label:'${name}', icon: "st.Appliances.appliances11"
	}

	valueTile("humidity", "device.humidity", width: 2, height: 2) {
		state "default", label:'30%', unit:"Humidity"
        //state "default", label:'${currentValue}%', unit:"Humidity"
	}

	standardTile("temperatureUnit", "device.temperatureUnit", decoration: "flat", width: 1, height: 1) {
		state "fahrenheit",  label: "°F", icon: "st.Weather.weather2", action:"setCelsius"
		state "celsius", label: "°C", icon: "st.Weather.weather2", action:"setFahrenheit"
	}



	main("tempreadout")
  //main(["temperature", "thermostatOperatingState"])


//"coolingSetpoint",
//"coolingSetpointDown",
//"coolingSetpointUp",
//"temperatureUnit",
//"presence",

	details([
            "temperature",
            
            "fireplace",            
            "thermostatOperatingState",            
            "thermostatMode",
                       
			"tempmin",
            "tempmax",
			"tempavg",
		    "refresh",
            
			"thermostatFanMode",
            
			"temperature1",
        	"temperature2",
        	"temperature3",
        	"temperature4",
            
          ])
    }
}

def installed() {
	sendEvent(name: "temperature", value: 21, unit: "C")
	sendEvent(name: "heatingSetpoint", value: 21, unit: "C")
	sendEvent(name: "thermostatSetpoint", value: 21, unit: "C")
	sendEvent(name: "coolingSetpoint", value: 21, unit: "C")
	sendEvent(name: "thermostatMode", value: "off")
	sendEvent(name: "thermostatFanMode", value: "fanAuto")
	sendEvent(name: "thermostatOperatingState", value: "idle")
	sendEvent(name: "humidity", value: 53, unit: "%")
}

def present(){
	sendEvent(name: "presence", value: "present")
}

def away(){
	sendEvent(name: "presence", value: "not present")
}

def fireplaceOff(){
	log.debug("fireplace off")
    sendHubCommand(get("fireplaceoff"))
	sendEvent(name: "fireplace", value: "off")
}

def fireplaceOn(){
	log.debug("fireplace on")
    sendHubCommand(get("fireplaceon"))
	sendEvent(name: "fireplace", value: "on")
}


def setHeatingSetpoint(Double temp) {
	log.debug "setHeatingSetpoint($temp)"
    //sendHubCommand(get(temp))
	sendEvent(name: "heatingSetpoint", value: temp)
	evaluate(device.currentValue("temperature"), temp, device.currentValue("coolingSetpoint"))
}

def setCoolingSetpoint(Double temp) {
	log.debug "setCoolingSetpoint($temp)"
    //sendHubCommand(get(temp))
	sendEvent(name: "coolingSetpoint", value: temp)
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), temp)
}

def setThermostatMode(String value) {
  	sendEvent(name: "tempreadout", value: value)
	sendEvent(name: "thermostatMode", value: value)
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def setThermostatFanMode(String value) {
	sendEvent(name: "thermostatFanMode", value: value)
}

def setCelsius() {
	log.debug "celsius"
	sendEvent(name: "temperatureUnit", value: "celsius")
}
def setFahrenheit() {
	log.debug "fahrenheit"
	sendEvent(name: "temperatureUnit", value: "fahrenheit")
}

def off() {
	sendHubCommand(get("off"))
	sendEvent(name: "thermostatMode", value: "off")
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def heat() {
	sendHubCommand(get("heat"))
	sendEvent(name: "thermostatMode", value: "heat")
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def auto() {
	log.debug "auto"
	//sendHubCommand(get("auto"))
    sendHubCommand(get("timer"))
	sendEvent(name: "thermostatMode", value: "auto")
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def timer() {
	log.debug "timer"
	//sendHubCommand(get("timer"))
	//sendEvent(name: "thermostatMode", value: "timer")
	//evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def cool() {
	sendEvent(name: "thermostatMode", value: "cool")
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def fanOn() {
	log.debug "fan on"
	sendEvent(name: "thermostatFanMode", value: "on")
}

def fanAuto() {
	log.debug "fan outo"
	sendEvent(name: "thermostatFanMode", value: "auto")
}

def fanCirculate() {
	log.debug "fan circulate"
	sendEvent(name: "thermostatFanMode", value: "circulate")
}
def poll() {
	log.debug "*************Executing poll *************"
    evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
    log.debug "*****************************************"
	//null
}

def tempUp() {
	def ts = device.currentState("temperature")
	def value = ts ? ts.integerValue + 1 : 72
	sendEvent(name:"temperature", value: value)
    test()
	evaluate(value, device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def tempDown() {
	def ts = device.currentState("temperature")
	def value = ts ? ts.integerValue - 1 : 72
	sendEvent(name:"temperature", value: value)
	evaluate(value, device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def setTemperature(value) {
	def ts = device.currentState("temperature")
	sendEvent(name:"temperature", value: value)
	evaluate(value, device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def heatingSetpointUp() {
	def ts = device.currentState("heatingSetpoint")
	def value = ts ? ts.integerValue + 1 : 68
	sendEvent(name:"heatingSetpoint", value: value)
	evaluate(device.currentValue("temperature"), value, device.currentValue("coolingSetpoint"))
}

def heatingSetpointDown() {
	def ts = device.currentState("heatingSetpoint")
	def value = ts ? ts.integerValue - 1 : 68
	sendEvent(name:"heatingSetpoint", value: value)
	evaluate(device.currentValue("temperature"), value, device.currentValue("coolingSetpoint"))
}


def coolingSetpointUp() {
	def ts = device.currentState("coolingSetpoint")
	def value = ts ? ts.integerValue + 1 : 76
	sendEvent(name:"coolingSetpoint", value: value)
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), value)
}

def coolingSetpointDown() {
	def ts = device.currentState("coolingSetpoint")
	def value = ts ? ts.integerValue - 1 : 76
	sendEvent(name:"coolingSetpoint", value: value)
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), value)
}


def getSensorTemps(){

	def currentState = sensor.currentState("temperature")
    def currentState2 = sensor2.currentState("temperature")
    def currentState3 = sensor3.currentState("temperature")
    def currentState4 = sensor4.currentState("temperature")
    def currentStateArray = sensorArr.currentState("temperature")
    
    def temps = [currentState.integerValue, currentState2.integerValue, currentState3.integerValue, currentState4.integerValue]
    def average = (temps.sum() / temps.size()).toInteger()
    def min = temps.min()
    def max = temps.max()
    
    log.debug "Average temp ${average}"
    log.debug "Min temp ${min}"
    log.debug "Max temp ${max}"

    log.debug "temperature value as a string: ${currentState.value}"
    log.debug "temperature value for all: ${currentState.value} ${currentState2.value} ${currentState3.value} ${currentState4.value} "
    log.debug "time this temperature record was created: ${currentState.date}"
    
    log.debug "temperature value as a string: ${currentStateArray.value}"
    log.debug "time this temperature record was created: ${currentStateArray.date}"
    
    //sendEvent(name: "temperature", value: currentState.value, unit: "C")
    sendEvent(name: "temperature", value: max, unit: "C")
    
    sendEvent(name: "temperature1", value: currentState.value, unit: "C")
    sendEvent(name: "temperature2", value: currentState2.value, unit: "C")
    sendEvent(name: "temperature3", value: currentState3.value, unit: "C")
    sendEvent(name: "temperature4", value: currentState4.value, unit: "C")
    sendEvent(name: "tempmin", value: min, unit: "C")
    sendEvent(name: "tempmax", value: max, unit: "C")
    sendEvent(name: "tempavg", value: average, unit: "C")
}

def evaluate(temp, heatingSetpoint, coolingSetpoint) {

	///We dont want to use the x10 temp sensor so we set it to an out of bounds cooling and heating value. We don't use it so we pad it. We use the temp sensor data from zigbee devices
	def x10heatvalue = 25
    def x10coolvalue = 21
    def x10offvalue = 18
	///
    
	def threshold = 1.0
	def current = device.currentValue("thermostatOperatingState")
	def mode = device.currentValue("thermostatMode")

	def heating = false
	def cooling = false
	def idle = false
	
    log.debug "-- evaluate($temp, $heatingSetpoint, $coolingSetpoint) at mode $mode"
    
    getSensorTemps()
	sendHubCommand(get(coolingSetpoint)) //this doesnt need to happen since we are replacing the temp sensor in the AC unit itself
        
//If Heat or Auto
	if (mode in ["heat","emergency heat","auto"]) {
log.debug "-- Heat or Auto  mode"
		if (heatingSetpoint - temp >= threshold) {
			heating = true
			sendEvent(name: "thermostatOperatingState", value: "heating")
		}
		else if (temp - heatingSetpoint >= threshold) {
			idle = true
		}
		sendEvent(name: "thermostatSetpoint", value: heatingSetpoint)
        sendHubCommand(get(heatingSetpoint))
	}
//If Cool or Auto
	if (mode in ["cool","auto"]) {
		log.debug "-- Cool or Auto mode"
    	sendEvent(name: "thermostatSetpoint", value: coolingSetpoint)
		if (temp - coolingSetpoint >= threshold) {
			log.debug "-- It's Time to cool"	
			cooling = true
            sendHubCommand(get("cool"))
            sendHubCommand(get(x10coolvalue))
			sendEvent(name: "thermostatOperatingState", value: "cooling")
		}
		else if (coolingSetpoint - temp >= threshold && !heating) {
        	log.debug "-- It's Time to idle"
			idle = true
			log.debug cooling
            log.debug idle
		}
	}
//If Off
	else { 
log.debug "-- Not Heat or Auto and note Cool or Auto"
		sendEvent(name: "thermostatSetpoint", value: heatingSetpoint)
        sendHubCommand(get(heatingSetpoint))
	}
//If Idle
	if (idle && !heating && !cooling) {
		log.debug "-- Idle and not heating or cooling"
		sendEvent(name: "thermostatOperatingState", value: "idle")
	}
}

private Integer convertHexToInt(hex) {

    Integer.parseInt(hex,16)
}

private String convertHexToIP(hex) {

    [convertHexToInt(hex[0..1]),convertHexToInt(hex[2..3]),convertHexToInt(hex[4..5]),convertHexToInt(hex[6..7])].join(".")
}

private getHostAddress() {
	log.debug device.deviceNetworkId
    def parts = device.deviceNetworkId.split(":")
    //def ip = convertHexToIP(parts[0])
   //def port = convertHexToInt(parts[1])
   def ip = parts[0]
   def port = parts[1]

    return device.deviceNetworkId
}

private get(path) {
    log.debug "GET ${path}"
	def rootpath = "/services/thermostat/ajax/set_therm_by_get.php?therm="

    def result = new physicalgraph.device.HubAction(
            method: "GET",
            path: rootpath + path,
            headers: [HOST:getHostAddress()]
    )
    return result
}

private subscribe( path) {

    log.debug "SUBSCRIBE ${path}"

    def result = new physicalgraph.device.HubAction(
            method: "SUBSCRIBE",
            path: path,
            headers: [HOST:getHostAddress()]
    )
}

private def parseEventMessage(String description) {
    def event = [:]
    def parts = description.split(',')
    parts.each { part ->
        part = part.trim()

        if (part.startsWith('headers')) {
            part -= "headers:"
            def valueString = part.trim()
            if (valueString) {
                event.headers = valueString
            }
        }
        else if (part.startsWith('body')) {
            part -= "body:"
            def valueString = part.trim()
            if (valueString) {
                event.body = valueString
            }
        }
    }

    event
}

private parse(String description) {
    log.debug "Parsing '${description}'"

    def parsedEvent= parseEventMessage( description)

    def headerString = new String(parsedEvent.headers.decodeBase64())
    def bodyString = new String(parsedEvent.body.decodeBase64())

    def json = new groovy.json.JsonSlurper().parseText( bodyString)

    log.trace json

    if( json.msg)
    {
        if( json.msg.startsWith("state"))
        {
            log.trace "Setting state"

            sendEvent (name: json.name, value: json.state)
        }
        else if( json.msg.startsWith("status"))
        {
            log.trace "Setting state from status"

            sendEvent (name: "door",  value: json.door)
        }
    }
}

private getCallBackAddress() {

    device.hub.getDataValue("localIP") + ":" + device.hub.getDataValue("localSrvPortTCP")
}

def locationHandler(evt) {
    def description = evt.description
    def hub = evt?.hubId

    def parsedEvent = parseEventMessage(description)
    parsedEvent << ["hub":hub]

//        log.trace "evt"+evt
    log.trace parsedEvent
}
