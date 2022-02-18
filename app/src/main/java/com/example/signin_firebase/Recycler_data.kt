package com.example.signin_firebase

class Recycler_data {
    var imageId:Int
    var bus_name:String
    private var bus_time:String
    private var avail_seats:String
    constructor(imageId: Int, bus_name: String, bus_time: String, avail_seats: String) {
        this.imageId = imageId
        this.bus_name = bus_name
        this.bus_time = bus_time
        this.avail_seats = avail_seats
    }


}