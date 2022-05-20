# Ant Colony Optimisation - TSP

Implementierung: Java 17.0.2 | keine Modellierung | keine JUnit-Tests

## Applikation 01 | 10 Punkte

Für die Problemstellung TSP mit der Dateninstanz a280 ist eine Konsolen-Applikation für die Suche mit BruteForce zu
realisieren.

## Applikation 02 | 30 Punkte

Für die Problemstellung TSP mit der Dateninstanz a280 ist eine Konsolen-Applikation für die parallelisierte Optimierung
mit wahlweise Particle Swarm Optimization, Ant Colony Optimization oder Artificial Bee Colony zu realisieren. Die Suche
der Agenten Particle, Ant oder Bee ist mit leistungsfähigen Technologien aus dem Paket java.util.concurrent zu
parallelisieren. Die Parameter sind dahingehend zu optimieren, dass bezüglich dem bekannten Optimium 2579 eine
Lösungsqualität von mindestens 95% erreicht wird. In einem Logfile ist das Schwarmverhalten nachvollziehbar zu
protokollieren.

## Applikation 03 | 15 Punkte

Für die Problemstellung TSP mit der Dateninstanz a280 ist eine Konsolen-Applikation für die parallelisierte Suche einer
bestmöglichen Parameterkonfiguration für den in der Applikation 02 angewandten Algorithmus zu realisieren. Die
bestmögliche Parameterkonfiguration wird in einer JSON-Datei gespeichert. Die Konsolen-Applikation zu Applikation 02 ist
dahingehend zu erweitern, dass über Kommandozeile `-best [dateiname]` die JSON-Datei spezifiziert bzw. angewandt wird.