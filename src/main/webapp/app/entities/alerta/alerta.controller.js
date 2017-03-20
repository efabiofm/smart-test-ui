(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('AlertaController', AlertaController);

    AlertaController.$inject = ['$scope', '$state', 'Alerta'];

    function AlertaController ($scope, $state, Alerta) {
        var vm = this;

        vm.alertas = [];

        loadAll();

        function loadAll() {
            Alerta.query(function(result) {
                vm.alertas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
