package com.example.signin_firebase

class Recycler_data {
    var imageId:Int
    var Name:String
    var Timing:String
    var avail_seats:String
    var Bus_No:String
    constructor(imageId: Int, bus_name: String, bus_time: String, avail_seats: String,bus_no:String) {
        this.imageId = imageId
        this.Name = bus_name
        this.Timing = bus_time
        this.avail_seats = avail_seats
        this.Bus_No=bus_no
    }


}