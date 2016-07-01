/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
preferences {
        input("ip", "string", title:"IP Address", description: "10.0.0.10", required: true, displayDuringSetup: false)
        input("port", "string", title:"Port", description: "80", defaultValue: 8000 , required: true, displayDuringSetup: false)
}
 
metadata {
	// Automatically generated. Make future change here.
	definition (name: "x10 Thermostat", namespace: "daggasoft", author: "daggasoft") {
		capability "Thermostat"
		capability "Relative Humidity Measurement"
        capability "Switch"

		command "tempUp"
		command "tempDown"
		command "heatUp"
		command "heatDown"
		command "coolUp"
		command "coolDown"
		command "setTemperature", ["number"]
	}

	tiles(scale: 2) {
    
        standardTile("switchTile", "device.temperature", width: 2, height: 2, canChangeIcon: true) { //main things list test switch, set in main
            state "default", label:'${currentValue}°', unit:"dC"
        }
		multiAttributeTile(name:"thermostatMulti", type:"thermostat", width:6, height:4) {  
			tileAttribute("device.temperature", key: "PRIMARY_CONTROL") {
				attributeState("default", label:'${currentValue}°', unit:"dC") // the label in the list of things
			}
			tileAttribute("device.temperature", key: "VALUE_CONTROL") {
				attributeState("VALUE_UP", action: "tempUp")
				attributeState("VALUE_DOWN", action: "tempDown")
			}
			tileAttribute("device.humidity", key: "SECONDARY_CONTROL") {
				attributeState("default", label:'${currentValue}%', unit:"%")
			}
			tileAttribute("device.thermostatOperatingState", key: "OPERATING_STATE") {
				attributeState("idle", backgroundColor:"#44b621")
				attributeState("heating", backgroundColor:"#ea5462")
				attributeState("cooling", backgroundColor:"#ff99cc")
			}
			tileAttribute("device.thermostatMode", key: "THERMOSTAT_MODE") {
                attributeState("timer", label:'${name}')
				attributeState("off", label:'${name}')
				attributeState("heat", label:'${name}')
				attributeState("cool", label:'${name}')
				attributeState("auto", label:'${name}')
			}
			tileAttribute("device.heatingSetpoint", key: "HEATING_SETPOINT") {
				attributeState("default", label:'${currentValue}°', unit:"dC")
			}
			tileAttribute("device.coolingSetpoint", key: "COOLING_SETPOINT") {
				attributeState("default", label:'${currentValue}°', unit:"dC")
			}
		}

		valueTile("temperature", "device.temperature", width: 2, height: 2) {
			state("temperature", label:'${currentValue}', unit:"dC",
				backgroundColors:[
					[value: 31, color: "#153591"],
					[value: 44, color: "#1e9cbb"],
					[value: 59, color: "#90d2a7"],
					[value: 74, color: "#44b621"],
					[value: 84, color: "#f1d801"],
					[value: 95, color: "#d04e00"],
					[value: 96, color: "#bc2323"]
				]
			)
		}
		standardTile("tempDown", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "default", label:'down', action:"tempDown"
		}
		standardTile("tempUp", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "default", label:'up', action:"tempUp"
		}

		valueTile("heatingSetpoint", "device.heatingSetpoint", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "heat", label:'${currentValue} heat', unit: "F", backgroundColor:"#ffffff"
		}
		standardTile("heatDown", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "default", label:'down', action:"heatDown"
		}
		standardTile("heatUp", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "default", label:'up', action:"heatUp"
		}

		valueTile("coolingSetpoint", "device.coolingSetpoint", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "cool", label:'${currentValue} cool', unit:"C", backgroundColor:"#ffffff"
		}
		standardTile("coolDown", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "default", label:'down', action:"coolDown"
		}
		standardTile("coolUp", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "default", label:'up', action:"coolUp"
		}

		standardTile("mode", "device.thermostatMode", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "off", label:'${name}', action:"thermostat.heat", backgroundColor:"#ffffff"
			state "heat", label:'${name}', action:"thermostat.cool", backgroundColor:"#ffa81e"
			state "cool", label:'${name}', action:"thermostat.auto", backgroundColor:"#269bd2"
			state "auto", label:'${name}', action:"thermostat.timer", backgroundColor:"#79b821"
            state "timer", label:'${name}', action:"thermostat.off", backgroundColor:"#ff99cc"
		}
		standardTile("fanMode", "device.thermostatFanMode", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "fanAuto", label:'${name}', action:"thermostat.fanOn", backgroundColor:"#ffffff"
			state "fanOn", label:'${name}', action:"thermostat.fanCirculate", backgroundColor:"#ffffff"
			state "fanCirculate", label:'${name}', action:"thermostat.fanAuto", backgroundColor:"#ffffff"
		}
		standardTile("operatingState", "device.thermostatOperatingState", width: 2, height: 2) {
			state "idle", label:'${name}', backgroundColor:"#ffffff"
			state "heating", label:'${name}', backgroundColor:"#ffa81e"
			state "cooling", label:'${name}', backgroundColor:"#269bd2"
		}

		//main "button"		
        main("switchTile")
		//main("thermostatMulti")		//this controls the whats shown on the things list
		details([					//I think this is the actual layout in the app
			"temperature","tempDown","tempUp",
			"heatingSetpoint", "heatDown", "heatUp",
			"coolingSetpoint", "coolDown", "coolUp",
            "mode", "fanMode", "operatingState",
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

def evaluate(temp, heatingSetpoint, coolingSetpoint) {
	log.debug "evaluate($temp, $heatingSetpoint, $coolingSetpoint"
	def threshold = 1.0
	def current = device.currentValue("thermostatOperatingState")
	def mode = device.currentValue("thermostatMode")

	def heating = false
	def cooling = false
	def idle = false
	if (mode in ["heat","emergency heat","auto"]) {
		if (heatingSetpoint - temp >= threshold) {
			heating = true
			sendEvent(name: "thermostatOperatingState", value: "heating")
		}
		else if (temp - heatingSetpoint >= threshold) {
			idle = true
		}
		sendEvent(name: "thermostatSetpoint", value: heatingSetpoint)
	}
	if (mode in ["cool","auto"]) {
		if (temp - coolingSetpoint >= threshold) {
			cooling = true
			sendEvent(name: "thermostatOperatingState", value: "cooling")
		}
		else if (coolingSetpoint - temp >= threshold && !heating) {
			idle = true
		}
		sendEvent(name: "thermostatSetpoint", value: coolingSetpoint)
	}
	else {
		sendEvent(name: "thermostatSetpoint", value: heatingSetpoint)
	}
	if (idle && !heating && !cooling) {
		sendEvent(name: "thermostatOperatingState", value: "idle")
	}
}

def setHeatingSetpoint(Double degreesF) {
	log.debug "setHeatingSetpoint($degreesF)"
    sendHubCommand(get(degreesF))
	sendEvent(name: "heatingSetpoint", value: degreesF)
	evaluate(device.currentValue("temperature"), degreesF, device.currentValue("coolingSetpoint"))
}

def setCoolingSetpoint(Double degreesF) {
	log.debug "setCoolingSetpoint($degreesF)"
    sendHubCommand(get(degreesF))
	sendEvent(name: "coolingSetpoint", value: degreesF)
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), degreesF)
}

def setThermostatMode(String value) {
	sendEvent(name: "thermostatMode", value: value)
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def setThermostatFanMode(String value) {
	sendEvent(name: "thermostatFanMode", value: value)
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
	sendHubCommand(get("auto"))
	sendEvent(name: "thermostatMode", value: "auto")
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def timer() {
	log.debug "timer"
	sendHubCommand(get("timer"))
	sendEvent(name: "thermostatMode", value: "timer")
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def emergencyHeat() {
	sendEvent(name: "thermostatMode", value: "emergency heat")
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def cool() {
	sendHubCommand(get("cool"))
	sendEvent(name: "thermostatMode", value: "cool")
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), device.currentValue("coolingSetpoint"))
}

def fanOn() {
	sendEvent(name: "thermostatFanMode", value: "fanOn")
}

def fanAuto() {
	sendEvent(name: "thermostatFanMode", value: "fanAuto")
}

def fanCirculate() {
	sendEvent(name: "thermostatFanMode", value: "fanCirculate")
}

def poll() {
	log.debug "Executing 'poll'"
    sendEvent(name: "switch", value: "off")
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

def heatUp() {
	def ts = device.currentState("heatingSetpoint")
	def value = ts ? ts.integerValue + 1 : 68
	sendEvent(name:"heatingSetpoint", value: value)
	evaluate(device.currentValue("temperature"), value, device.currentValue("coolingSetpoint"))
}

def heatDown() {
	def ts = device.currentState("heatingSetpoint")
	def value = ts ? ts.integerValue - 1 : 68
	sendEvent(name:"heatingSetpoint", value: value)
	evaluate(device.currentValue("temperature"), value, device.currentValue("coolingSetpoint"))
}


def coolUp() {
	def ts = device.currentState("coolingSetpoint")
	def value = ts ? ts.integerValue + 1 : 76
	sendEvent(name:"coolingSetpoint", value: value)
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), value)
}

def coolDown() {
	def ts = device.currentState("coolingSetpoint")
	def value = ts ? ts.integerValue - 1 : 76
	sendEvent(name:"coolingSetpoint", value: value)
	evaluate(device.currentValue("temperature"), device.currentValue("heatingSetpoint"), value)
}


////

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

private get( path) {
	log.debug "yes i still work"
    log.debug "GET ${path}"
	def rootpath = "/services/thermostat/ajax/set_therm_by_get.php?therm="

    def result = new physicalgraph.device.HubAction(
            method: "GET",
            path: rootpath + path,
            headers: [HOST:getHostAddress()]
    )
    return result
}

private test(){
	sendHubCommand(get("test"))
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

//////


//V2

//