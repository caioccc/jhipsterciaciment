import { element, by, ElementFinder } from 'protractor';

export class ClienteComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-cliente div table .btn-danger'));
    title = element.all(by.css('jhi-cliente div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class ClienteUpdatePage {
    pageTitle = element(by.id('jhi-cliente-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomeInput = element(by.id('field_nome'));
    telefoneInput = element(by.id('field_telefone'));
    enderecoInput = element(by.id('field_endereco'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNomeInput(nome) {
        await this.nomeInput.sendKeys(nome);
    }

    async getNomeInput() {
        return this.nomeInput.getAttribute('value');
    }

    async setTelefoneInput(telefone) {
        await this.telefoneInput.sendKeys(telefone);
    }

    async getTelefoneInput() {
        return this.telefoneInput.getAttribute('value');
    }

    async setEnderecoInput(endereco) {
        await this.enderecoInput.sendKeys(endereco);
    }

    async getEnderecoInput() {
        return this.enderecoInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class ClienteDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-cliente-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-cliente'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
