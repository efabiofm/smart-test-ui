(function () {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('JhiTestsController', JhiTestsController);

    JhiTestsController.$inject = ['$cookies', '$http', 'JhiTestsService', 'EjecucionPrueba'];

    function JhiTestsController ($cookies, $http, JhiTestsService, EjecucionPrueba) {
        // This controller uses a Websocket connection to receive user activities in real-time.
        var vm = this;

        vm.pruebas = [];

        loadEjecuciones();
        refreshEjecuciones();

        function loadEjecuciones(){
            EjecucionPrueba.query().$promise.then(function (resultado){
                vm.pruebas = resultado;
                cambiarEstado();

            });
        }


        function cambiarEstado() {
            console.log("entra");
            for (var x in vm.pruebas) {
               console.log(x);
            }
        }

        function refreshEjecuciones() {
            setInterval(loadEjecuciones, 3000);
        }

        vm.tests = [];

        // console.log("c y s");
        // JhiTestsService.subscribe();
        // JhiTestsService.connect();

        // JhiTestsService.receive().then(null, null, function(test) {
        //     console.log(test)
        //     vm.tests.push(test);
        // });

    }
})();
