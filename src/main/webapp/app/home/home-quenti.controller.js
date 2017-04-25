(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HomeQuentiController', HomeQuentiController);

    HomeQuentiController.$inject = ['pruebas', 'sesion', 'Prueba',  'EjecucionPrueba', '$state'];

    function HomeQuentiController (pruebas, sesion, Prueba, EjecucionPrueba, $state) {
        var vm = this;
        vm.pruebas = pruebas;
        vm.cargarEjecucion = cargarEjecucion;
        vm.ejecutarPrueba = ejecutarPrueba;
        vm.listaMetodos = [
            //"GET",
            "POST"
            //"PUT",
            //"DELETE"
        ];
        vm.ejecucion = {
            metodo: "POST",
            body: "{}",
            headers: {
                token: sesion.token
            },
            params: []
        };
        vm.agregarParam = agregarParam;
        vm.removerParam = removerParam;
        vm.mostrarParams = true;
        vm.ejecucionSelec = {};

        /*Obtiene URL a invocar para ejecutar la prueba segun el id de Prueba*/

        function cargarEjecucion(ejecucion){
            Prueba.getURI({id : ejecucion.id}).$promise.then(function (response) {
                vm.ejecucion.url = response.url;
                vm.ejecucion.params = response.parametros;
                vm.ejecucion.body = response.body;
                vm.ejecucion.headers.serviceGroupId = response.serviceGroupId;
                vm.ejecucion.headers.serviceProviderId = response.serviceProviderId;
            });

        }

        function ejecutarPrueba(ejecucion) {
            //TODO: Enviar el service-provider-id y service-group-id en las ejecuciones que lo necesiten

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
                jhUserId: sesion.userId, //id del usuario que ejecuta la prueba,
                jhUserName: sesion.userName,
                serviceProviderId: ejecucion.headers.serviceProviderId,
                serviceGroupId: ejecucion.headers.serviceGroupId
            }

            EjecucionPrueba.ejecutarPrueba(ejecParaEnviar).$promise.then(function (response) {
                $state.go("ejecucion-prueba");
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
