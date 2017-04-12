(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HomeQuentiController', HomeQuentiController);

    HomeQuentiController.$inject = ['pruebas', 'sesion', 'Prueba',  'EjecucionPrueba'];

    function HomeQuentiController (pruebas, sesion, Prueba, EjecucionPrueba) {
        var vm = this;
        vm.pruebas = pruebas;
        vm.cargarEjecucion = cargarEjecucion;
        vm.ejecutarPrueba = ejecutarPrueba;
        vm.listaMetodos = [
            "GET", "POST", "PUT", "DELETE"
        ];
        vm.ejecucion = {
            metodo: "POST",
            body: "{}",
            headers: {
                token: sesion.token
            }
        };
        vm.agregarParam = agregarParam;
        vm.removerParam = removerParam;
        vm.mostrarParams = true;

        /*Obtiene URL a invocar para ejecutar la prueba segun el id de Prueba*/

        function cargarEjecucion(ejecucion){
            Prueba.getURI({id : ejecucion.id}).$promise.then(function (response) {
                vm.ejecucion.url = response.url;
                vm.ejecucion.params = response.parametros;
            });

        }

        function ejecutarPrueba(ejecucion) {
            vm.ejecutando = true; //muestra el icono dando vueltas
            var fullUrl = ejecucion.url;
            if(ejecucion.params.length > 0){ //pegar los parametros al URL
                fullUrl += "?";
                for(var i=0; i < ejecucion.params.length; i++){
                    fullUrl += ejecucion.params[i].nombre + "=" + ejecucion.params[i].valor;
                    if(i < ejecucion.params.length-1){
                        fullUrl += "&";
                    }
                }
            }

            //DTO de ejecucion-prueba que se va a generar
            var ejecParaEnviar = {
                url: fullUrl,
                pruebaId: vm.ejecucionSelec.id,
                activo: true,
                body: ejecucion.body,
                fecha: new Date(),
                jhUserId: sesion.userId //id del usuario que ejecuta la prueba
            }

            EjecucionPrueba.ejecutarPrueba(ejecParaEnviar).$promise.then(function (response) {
                vm.ejecutando = false;
                vm.ejecucion.respuesta = JSON.stringify(response);
            });
        }

        function agregarParam(){
            vm.ejecucion.params.push({
                nombre: null,
                valor: null
            });
        }

        function removerParam(index){
            vm.ejecucion.params.splice(index, 1);
        }
    }
})();
