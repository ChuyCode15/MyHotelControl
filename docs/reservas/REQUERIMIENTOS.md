# Requerimientos: Sistema de Reservas

Para definir de manera clara y explícita el sistema de reservas, por favor responde a las siguientes preguntas:

~~~
### TE VOY A DAR ALGO DE CONTEXTO :

El sistema es un sistema multitenant entonces esta diseñado para muchos clientes  o mini hoteles diferentes competimos contra las libretas asi que se busca que tabien el usuario pueda personalizar su hotel ejemplo:
- las tarifas si tenemos un modulo de control de las tarifas entonces tenemos un sistema donde define el cliente sis tarifas y agrega sus reglas de negocio por decirlo 
- tarifa 1 , esta tarifa es la default osea simpre que no exista al alguna otra regla o se ajuste manual esta tarifa sera la principal.  
- tarifa 2 , se controla por fecha (fecha inicio xx/xx/xxxx fecha final xx/xx/xxxx)
Lo que quiero dejar en claro es que el sistema tiene muchisimo modulos que trabajar ha detalle, pero no podemos desarollarlos todos al mismo tiempo,
y asi que devemos hacer un mapa de todo divido por partes odne esten las partes principales omo lo modulos primordiales y leugo los sub modulo syluego lo sdetalles de operacion  
asi mismo deveriamos empezar a implementarlos de poco a poco no supongamos nada ni inventemos si inventamos algo podriamos generar deuda tecnica,
por lo mismo lo que deveriamos hacer es poner un comentario com alguan descipcion y en el flujo de de dearolo agregar un puto de accion pendiente y para no romper la construccion dejar una impelemtacion solida por ahora pero que este pendiuente esta documantado para abordarlo despues 
es decir el modulo de precio no nos interesa ahora pero podrias manejrarlo de manea simple en la entiodad si hay 5 precion 1 al 5 y a la hora de tregistrar podrias asignarlo por ahora simpre el 1 pero dejar la opcion de poner el precio 3 en alguna nota
- po reso es muy importante construir pero dejar en claro que hay partes quie contruimos ais poque falta de atecion al detalle pero la documantamos seguimos con tiu ciestioanmri de la fase 1 modulo 1 redservas la version 1
~~~

## 1. Detalles de la Reserva
- ¿Qué datos mínimos debe tener una reserva? (Ej: datos del huésped, fechas, habitación, estado, precio total, adelanto pagado, notas).
 - R: id, huespedId, nombreHuesped, habitacionId, numeroHabitacion, fechaEntrada, fechaSalida, cantidadNoches, disponibilidades, precioPorNoche, montoAnticipo, montoTotal, estado, fechaLimiteConfirmacion, fechaCreacion, activo   

- ¿Se manejarán diferentes tipos de huéspedes? (Ej: personas individuales, grupos, empresas).
 - R: esto es completamente opcional por ejempo es decir no es prioridad ni pegurnarlo pero si lo requiero devo dener al capaciada de manejarlo. 
 - 
- ¿Cómo se manejan los cambios de tarifa según la temporada o tipo de cliente?
 - R: El cliente comento que ellos definen las tarifas por año en un tabulador la idea pricipal es cada habitacion tiene hasta 5 tipos de tarifa y estas estan aplicada a travez de las reglas de negocio (haun no se ha definido despues refactoizmaso cuansdo se aplican dem naera automatica como las fdehacjhas este sera un modulo de precion donde tu pones las tarifas 1 2 3 l)
 - 
## 2. CRUD y Ciclo de Vida
- ¿Cuáles son los estados exactos por los que pasa una reserva? (Ej: Solicitud, Pendiente de Pago, Confirmada, Check-in, Check-out, Cancelada, No-Show).
- ¿Qué reglas de negocio definen la transición entre estos estados? (Ej: ¿Una reserva confirmada se puede cancelar automáticamente si no se recibe el pago en 24h?)
 - R: "SOLICITUD"si la reserva la hace el ciente en linea internet o algun medio el soslo es solicitu (se tiene que confirmar con el personal), "RESERVADO" uan reserva echa por el personal por llamada o confirmad por el personal sin confirmacion "CONFIRMAdA" cualquier reserva que ya tenga un anticpo de pago confirmado, "CANCELADA" UAN RESERVA CANCELADA NO SE BORRA ES UN REGISTROI DEL CLIENTE OIBSERVACION Y TIPO DE PERFIL, "HOSPEDAJE" una reserva exitosa que marco un checkin
 las reglas de nogocio haun son ambiguas poque muchas tiene que ser validad y gerardas porl cliente tenant asi qeu por ahora las reglas las definiremos refactozando durante el desarollo poque estasmo en al fase1 donnde se nesesita un mvp

## 3. Estrategias de Control
- ¿Cuál es la política de sobreventa? (Ej: ¿El sistema permite sobreventa o es estrictamente bloqueante?)
 no el sistema no deve permitir sobre venta  nunca pero si puede lanzar alertas a una reserva sin confirmar si tener incidenci en reservar y esta ocupado pero exiten reservas sin confirmacion el sistema puede peridrleel ciente que si deja su correo le ocnfirmaremos si encontramos algo disponible entocnes el sistema reliza un filtro de reservs sin confimar que coican con la busqueda del cleinte y este se lo envia como alerta al al administrador puedallamar y genera petioon si no confirma con un antcipo en 24hrs puede perder su reserva y si la piertde le permitimo slareserva al cli ente nuevo solo con anticipo  
- Sobre los anticipos: ¿Es fijo o porcentual? ¿Cómo se registra este pago en el sistema?
 R:los aticipos deverria ser porcentual o fijhos pero esto sera un  modulode control personlizabel por el administrador del tenat cada tenat puede tenr valores diferentes entonces devemos tenrr la capacidad de personlizacion 

## 4. Disponibilidad y Búsqueda
- ¿Cómo se define la "disponibilidad" de una habitación? (Ej: ¿Solo por fechas? ¿Se consideran estados de mantenimiento?)
 R: estado se mantenimiento suena interesante seria una especia de reserva marcada por el estado de mantenimineto donde el administrador la pone en mantenimiendo de esta fecha a esta fecha parsa quie se pueda ocupar cuando se nesesite y este libre 

- ¿El sistema debe soportar cambios de habitación durante la estancia?
   si esto podria estar permitido por peticon del cleinte o se peuda ofrecer por que este lleno por decirlo asi tengo 3 dias en una y 2 en tro disculpemenpero es lo que se puede ofrecerle un cambio de habitacion por reservas(es profecional o lo cancelasmo lo del cambio por reservas previas)
## 5. Integración Frontend (Angular)
- ¿Qué información necesita el frontend en la vista de búsqueda inicial?
 - R: la vista de reserva que trabajaremos por ahora sera la de vista del lado del uaurio empleado del mini hotel este empleado tiene un sistema wen donde la pestañ de resservas le mustra las opcionesde reserva viendo una pantalla tipo char de hoteleria, este seriacomo un boz en el centro de la pantalla que se ve el calendario yo escojo en dos pestaña el año o el mes una, vez abierto seleciono el dia de entrada y las noches de estancia me pide la cantidad e personas y niños y se genre un filtro que muestre las habitaciones disponibles para reservar que cumplan las condiones marcando el tipo de habitacion si el clinete lo pide si el cliene dice economico pues hay ya sale  el filtros
tiene que ser algo muy intuitivo que no se ocupe entrenamiento si no logica 