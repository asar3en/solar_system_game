package org.solar_system_game.sim;

//data primarily taken from: https://nssdc.gsfc.nasa.gov/planetary/factsheet/

public class SolarSystemParameters {

    public static final double G = 6.67300E-20;
    public static final double[] celestialBodyMasses = {
            //---Sun & Planets
            1.9885*Math.pow(10, 30), //Sun
            3.3011*Math.pow(10, 23), //Mercury
            4.8675*Math.pow(10, 24), //Venus
            5.972*Math.pow(10, 24), //Earth
            6.4171*Math.pow(10, 23), //Mars
            1.8982*Math.pow(10, 27), //Jupiter
            5.6834*Math.pow(10, 26), //Saturn
            8.6810*Math.pow(10, 25), //Uranus
            1.024*Math.pow(10, 26), //Neptune
            1.303*Math.pow(10, 22), //Pluto

            //---Moons
            7.342*Math.pow(10, 22), //Earth's Moon
            1.060*Math.pow(10, 16), //Mars' Phobos
            1.51*Math.pow(10, 15), //Mars' Deimos

    };
    //in kg

    public static final double[] celestialBodyMeanRadii = {
            //---Sun & Planets
            696300, //Sun
            2439.7, //Mercury
            6051.8, //Venus
            6371.0, //Earth
            3389.5, //Mars
            69911.0, //Jupiter
            58232.0, //Saturn
            25362.0, //Uranus
            24622.0, //Neptune
            1188.3, //Pluto

            //--Moons
            1737.4, //Earth Moon
            11.08, //Mars' Phobos
            6.27, //Mars' Deimos
    };
    //in km

    public static final double[] celestialBodyMeanDist = {
            //---Sun & Planets
            0.0, //Sun
            57.9*Math.pow(10, 6), //Mercury
            108.2*Math.pow(10, 6), //Venus
            149.6*Math.pow(10, 6), //Earth
            228.0*Math.pow(10,6), //Mars
            778.5*Math.pow(10,6), //Jupiter
            1432.0*Math.pow(10,6), //Saturn
            2867*Math.pow(10,6), //Uranus
            4515.0*Math.pow(10,6), //Neptune
            5906.0*Math.pow(10,6), //Pluto

            //--Moons
            0.384*Math.pow(10,6), //Earth Moon
            9376.0, //Mars' Phobos
            23463.0 //Mars' Deimos
    };
    //in km

    public static final double[] celestialBodyMeanOrbitalVelocities = {
            //---Sun & Planets
            47.4, //Mercury
            35.0, //Venus
            29.8, //Earth
            24.1, //Mars
            13.1, //Jupiter
            9.7, //Saturn
            6.8, //Uranus
            5.4, //Neptune
            4.7, //Pluto

            //--Moons
            1.0, //Earth Moon
            2.14, //Phobos
            1.35, //Deimos
    };
    //in km/s
    //used only to determine the initial velocities, which will later be altered
    //by accelerations resulting from forces acting between the celestial bodies.


}
