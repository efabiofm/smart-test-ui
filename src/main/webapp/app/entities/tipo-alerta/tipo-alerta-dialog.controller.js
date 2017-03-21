(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoAlertaDialogController', TipoAlertaDialogController);

    TipoAlertaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoAlerta', 'Alerta'];

    function TipoAlertaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoAlerta, Alerta) {
        var vm = this;

        vm.tipoAlerta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.alertas = Alerta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoAlerta.id !== null) {
                TipoAlerta.update(vm.tipoAlerta, onSaveSuccess, onSaveError);
            } else {
                TipoAlerta.save(vm.tipoAlerta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:tipoAlertaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
