(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ambiente', {
            parent: 'entity',
            url: '/ambiente',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.ambiente.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ambiente/ambientes.html',
                    controller: 'AmbienteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ambiente');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ambiente-detail', {
            parent: 'entity',
            url: '/ambiente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.ambiente.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ambiente/ambiente-detail.html',
                    controller: 'AmbienteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ambiente');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ambiente', function($stateParams, Ambiente) {
                    return Ambiente.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ambiente',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ambiente-detail.edit', {
            parent: 'ambiente-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ambiente/ambiente-dialog.html',
                    controller: 'AmbienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ambiente', function(Ambiente) {
                            return Ambiente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ambiente.new', {
            parent: 'ambiente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ambiente/ambiente-dialog.html',
                    controller: 'AmbienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                descripcion: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ambiente', null, { reload: 'ambiente' });
                }, function() {
                    $state.go('ambiente');
                });
            }]
        })
        .state('ambiente.edit', {
            parent: 'ambiente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ambiente/ambiente-dialog.html',
                    controller: 'AmbienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ambiente', function(Ambiente) {
                            return Ambiente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ambiente', null, { reload: 'ambiente' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ambiente.delete', {
            parent: 'ambiente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ambiente/ambiente-delete-dialog.html',
                    controller: 'AmbienteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ambiente', function(Ambiente) {
                            return Ambiente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ambiente', null, { reload: 'ambiente' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
