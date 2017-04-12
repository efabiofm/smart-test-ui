(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HomeQuentiController', HomeQuentiController);

    HomeQuentiController.$inject = ['pruebas', 'token', 'Prueba',  'EjecucionPrueba'];

    function HomeQuentiController (pruebas, token, Prueba, EjecucionPrueba) {
        var vm = this;
        vm.pruebas = pruebas;
        vm.cargarEjecucion = cargarEjecucion;
        vm.ejecutarPrueba = ejecutarPrueba;
        vm.listaMetodos = [
            "GET", "POST", "PUT", "DELETE"
        ];
        vm.ejecucion = {
            metodo: "POST",
            headers: {
                token: token
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
            var ejecucionCopy = angular.copy(ejecucion); //para no modificar el que se muestra en el html
            var fullUrl = ejecucion.url;
            if(ejecucion.params.length > 0){
                fullUrl += "?";
                for(var i=0; i < ejecucion.params.length; i++){
                    fullUrl += ejecucion.params[i].nombre + "=" + ejecucion.params[i].valor;
                    if(i < ejecucion.params.length-1){
                        fullUrl += "&";
                    }
                }
            }
            ejecucionCopy.url = fullUrl;
            EjecucionPrueba.ejecutarPrueba(ejecucionCopy).$promise.then(function (response) {
                console.log(response);
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
