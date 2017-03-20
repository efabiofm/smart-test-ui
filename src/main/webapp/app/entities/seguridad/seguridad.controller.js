(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('SeguridadController', SeguridadController);

    SeguridadController.$inject = ['$scope', '$state', 'Seguridad'];

    function SeguridadController ($scope, $state, Seguridad) {
        var vm = this;

        vm.seguridads = [];

        loadAll();

        function loadAll() {
            Seguridad.query(function(result) {
                vm.seguridads = result;
                vm.searchQuery = null;
            });
        }
    }
})();
