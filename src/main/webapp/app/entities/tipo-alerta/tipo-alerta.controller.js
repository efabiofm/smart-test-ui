(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoAlertaController', TipoAlertaController);

    TipoAlertaController.$inject = ['$scope', '$state', 'TipoAlerta'];

    function TipoAlertaController ($scope, $state, TipoAlerta) {
        var vm = this;

        vm.tipoAlertas = [];

        loadAll();

        function loadAll() {
            TipoAlerta.query(function(result) {
                vm.tipoAlertas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
