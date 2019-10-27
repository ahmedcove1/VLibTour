This module is a library and proposes classes that are used other
Maven modules, which are components of the architecture.

For instance, the class Position defines the node of the graph for the
emulation. A position contains (by delegation) a GPSPosition. It is
assumed that some of the positions are Points Of Interest (POI). The
class Position then possesses a reference to an Object that will refer
to your version of the class POI.
