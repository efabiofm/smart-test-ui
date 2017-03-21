(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoAlertaDeleteController',TipoAlertaDeleteController);

    TipoAlertaDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoAlerta'];

    function TipoAlertaDeleteController($uibModalInstance, entity, TipoAlerta) {
        var vm = this;

        vm.tipoAlerta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoAlerta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
