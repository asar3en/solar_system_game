package org.solar_system_game.view;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.solar_system_game.sim.CelestialBody;
import org.solar_system_game.sim.SolarSystemParameters;
import org.solar_system_game.sim.Spaceship;
import org.solar_system_game.view.graphics.RenderObject;
import org.solar_system_game.view.graphics.Renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Scenario {

    CelestialBody[] celestialBodies;
    Map<String, Pair<Double, Double>> realCelPositions;
    Map<String, Double> radii;

    private Spaceship spaceshipForSim;

    public Scenario(CelestialBody[] celestialBodies) {
        spaceshipForSim = new Spaceship(
                110000,
                24000,
                10
        );

        this.celestialBodies = celestialBodies;
        realCelPositions = new HashMap<>();
        radii = new HashMap<>();
        for(CelestialBody body : celestialBodies) {
            realCelPositions.put(body.Name, new Pair<>(body.bodyCoordinates[0], body.bodyCoordinates[1]));
            radii.put(body.Name, body.radius);
        }

        realCelPositions.put("SpaceShip", new Pair<>(spaceshipForSim.bodyCoordinates[0], spaceshipForSim.bodyCoordinates[1]));
    }

    //When celestial body set is left unspecified it creates solar system by default
    public Scenario() {
        spaceshipForSim = new Spaceship(
                110000,
                24000,
                10
        );

        CelestialBody Sun = new CelestialBody(
                SolarSystemParameters.celestialBodyMasses[0],
                SolarSystemParameters.celestialBodyMeanRadii[0],
                0, 0, 0, "Sun");
        CelestialBody Mercury = new CelestialBody(
                SolarSystemParameters.celestialBodyMasses[1],
                SolarSystemParameters.celestialBodyMeanRadii[1],
                SolarSystemParameters.celestialBodyMeanDist[1],
                SolarSystemParameters.celestialBodyMeanOrbitalVelocities[0], 1, "Mercury");
        Mercury.initCoordinates();
        CelestialBody Venus = new  CelestialBody(
                SolarSystemParameters.celestialBodyMasses[2],
                SolarSystemParameters.celestialBodyMeanRadii[2],
                SolarSystemParameters.celestialBodyMeanDist[2],
                SolarSystemParameters.celestialBodyMeanOrbitalVelocities[1], 2, "Venus");
        Venus.initCoordinates();
        CelestialBody Earth = new  CelestialBody(
                SolarSystemParameters.celestialBodyMasses[3],
                SolarSystemParameters.celestialBodyMeanRadii[3],
                SolarSystemParameters.celestialBodyMeanDist[3],
                SolarSystemParameters.celestialBodyMeanOrbitalVelocities[2], 3, "Earth");
        Earth.initCoordinates();
        CelestialBody Mars = new  CelestialBody(
                SolarSystemParameters.celestialBodyMasses[4],
                SolarSystemParameters.celestialBodyMeanRadii[4],
                SolarSystemParameters.celestialBodyMeanDist[4],
                SolarSystemParameters.celestialBodyMeanOrbitalVelocities[3], 4, "Mars");
        Mars.initCoordinates();
        CelestialBody Jupiter = new  CelestialBody(
                SolarSystemParameters.celestialBodyMasses[5],
                SolarSystemParameters.celestialBodyMeanRadii[5],
                SolarSystemParameters.celestialBodyMeanDist[5],
                SolarSystemParameters.celestialBodyMeanOrbitalVelocities[4], 5, "Jupiter");
        Jupiter.initCoordinates();
        CelestialBody Saturn = new  CelestialBody(
                SolarSystemParameters.celestialBodyMasses[6],
                SolarSystemParameters.celestialBodyMeanRadii[6],
                SolarSystemParameters.celestialBodyMeanDist[6],
                SolarSystemParameters.celestialBodyMeanOrbitalVelocities[5], 6, "Saturn");
        Saturn.initCoordinates();
        CelestialBody Uranus = new  CelestialBody(
                SolarSystemParameters.celestialBodyMasses[7],
                SolarSystemParameters.celestialBodyMeanRadii[7],
                SolarSystemParameters.celestialBodyMeanDist[7],
                SolarSystemParameters.celestialBodyMeanOrbitalVelocities[6], 7, "Uranus");
        Uranus.initCoordinates();
        CelestialBody Neptune = new  CelestialBody(
                SolarSystemParameters.celestialBodyMasses[8],
                SolarSystemParameters.celestialBodyMeanRadii[8],
                SolarSystemParameters.celestialBodyMeanDist[8],
                SolarSystemParameters.celestialBodyMeanOrbitalVelocities[7], 8, "Neptune");
        Neptune.initCoordinates();
        CelestialBody Pluto = new  CelestialBody(
                SolarSystemParameters.celestialBodyMasses[9],
                SolarSystemParameters.celestialBodyMeanRadii[9],
                SolarSystemParameters.celestialBodyMeanDist[9],
                SolarSystemParameters.celestialBodyMeanOrbitalVelocities[8], 2, "Pluto");
        Pluto.initCoordinates();
        celestialBodies = new CelestialBody[]{
                Sun,Mercury,Venus,Earth,Mars,Jupiter,Saturn,Uranus,Neptune,Pluto
        };
        realCelPositions = new HashMap<>();
        radii = new HashMap<>();
        for(CelestialBody body : celestialBodies) {
            realCelPositions.put(body.Name, new Pair<>(body.bodyCoordinates[0], body.bodyCoordinates[1]));
            radii.put(body.Name, body.radius);
        }
        radii.put("SpaceShip", 0.06);

        realCelPositions.put("SpaceShip", new Pair<>(spaceshipForSim.bodyCoordinates[0], spaceshipForSim.bodyCoordinates[1]));
    }

    public Spaceship GetSimSpaceship() {
        return spaceshipForSim;
    }
    public Map<String, Double> GetRadii() {
        return radii;
    }
    public CelestialBody[] GetCelestialBodies() {
        return celestialBodies;
    }

    public void AddRenderObjectsToRenderer(Renderer renderer) {
        Random random = new Random();

        for(CelestialBody body : celestialBodies) {
            if(body.Name.equals("Sun"))
                renderer.AddToRenderList(new RenderObject(
                        Color.YELLOW, body.Name));
            else
                renderer.AddToRenderList(new RenderObject(
                    new Color( random.nextDouble(),  random.nextDouble(),  random.nextDouble(), 1.0), body.Name));
        }

    }
}
