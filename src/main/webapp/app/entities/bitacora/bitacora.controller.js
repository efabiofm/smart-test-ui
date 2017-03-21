(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('BitacoraController', BitacoraController);

    BitacoraController.$inject = ['$scope', '$state', 'Bitacora'];

    function BitacoraController ($scope, $state, Bitacora) {
        var vm = this;

        vm.bitacoras = [];

        loadAll();

        function loadAll() {
            Bitacora.query(function(result) {
                vm.bitacoras = result;
                vm.searchQuery = null;
            });
        }
    }
})();
