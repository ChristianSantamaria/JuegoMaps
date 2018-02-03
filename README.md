<h2>Juego de la busqueda del tesoro</h2>

La aplicacion se forma de forma en la que hay un Activity Main(MapsActivity) y una clase para poder leer los codigos QR.
Antes de nada para hacer una aplicacion con maps necesitamos la clave API que te la dan em Google Console Developer.

![alt text](https://github.com/ChristianSantamaria/JuegoMaps/blob/master/FotosReadMe/Captura4.PNG)

Una vez tenemos la API ya dentro y no dado ningun error procedemos a crear el tesoro, es decir, elegir una posicion en el mapa y creandolo

![alt text](https://github.com/ChristianSantamaria/JuegoMaps/blob/master/FotosReadMe/Captura5.PNG)

Despues de crear el tesoro podemos proceder a como sera la vista de la camara, con que zoom, fijado en alguo...

![alt text](https://github.com/ChristianSantamaria/JuegoMaps/blob/master/FotosReadMe/Captura6.PNG)

En mi caso al ir caminando me aparecera un icono con los metros de diferencia hacia el tesoro, y que si son menos de 20 metros se cree un radio circular dibujado en el mapa indicando que se encuentra ahí y por ultimo se esta a menos de 3 metros coloco una X indicando que esta muy cerca.

![alt text](https://github.com/ChristianSantamaria/JuegoMaps/blob/master/FotosReadMe/Captura3.PNG)
![alt text](https://github.com/ChristianSantamaria/JuegoMaps/blob/master/FotosReadMe/Captura8.PNG)

![alt text](https://github.com/ChristianSantamaria/JuegoMaps/blob/master/FotosReadMe/Captura.PNG)

Para leer el codigo QR y que devuelva un valor al main simplemete hay que hacer un Intent que coja como valor el resultado del QR y que la mande al main

![alt text](https://github.com/ChristianSantamaria/JuegoMaps/blob/master/FotosReadMe/Captura9.PNG)

Y en el main valido ese valor recibido en mia caso si "Tesoro1" == "Tesoro1" pues conseguiria el tesoro

En mi aplicaccion puse 2 tesoros mas al ir recojiendolos, para ello lo unico que tuve que hacer fue cambiar las cordenadas del tesoro una vez se descubra

![alt text](https://github.com/ChristianSantamaria/JuegoMaps/blob/master/FotosReadMe/Captura10.PNG)
