(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('AlertaDeleteController',AlertaDeleteController);

    AlertaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Alerta'];

    function AlertaDeleteController($uibModalInstance, entity, Alerta) {
        var vm = this;

        vm.alerta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Alerta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
