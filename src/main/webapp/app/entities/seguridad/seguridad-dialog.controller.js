(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('SeguridadDialogController', SeguridadDialogController);

    SeguridadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Seguridad'];

    function SeguridadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Seguridad) {
        var vm = this;

        vm.seguridad = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.seguridad.id !== null) {
                Seguridad.update(vm.seguridad, onSaveSuccess, onSaveError);
            } else {
                Seguridad.save(vm.seguridad, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:seguridadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
