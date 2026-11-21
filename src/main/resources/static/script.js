async function listarEquipamentos() {
    const resposta = await fetch("/equipamentos");
    const equipamentos = await resposta.json();

    const lista = document.getElementById("listaEquipamentos");

    lista.innerHTML = "";

    equipamentos.forEach(equipamento => {
        const item = document.createElement("li");

        item.textContent =
            equipamento.patrimonio + " - " +
            equipamento.nome + " - " +
            equipamento.tipo + " - " +
            (equipamento.disponivel ? "Disponível" : "Indisponível");

        lista.appendChild(item);
    });
    return equipamentos;
}

listarEquipamentos();
document.getElementById("formEquipamento")
    .addEventListener("submit", async function(event) {

        event.preventDefault();

        const equipamento = {
            patrimonio: document.getElementById("patrimonio").value,
            nome: document.getElementById("nomeEquipamento").value,
            tipo: document.getElementById("tipoEquipamento").value
        };

        try {
            const resposta = await fetch("/equipamentos", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(equipamento)
            });

            if (!resposta.ok) {
                throw new Error("Não foi possível cadastrar o equipamento.");
            }

            document.getElementById("mensagemEquipamento").textContent =
                "Equipamento cadastrado com sucesso!";

            document.getElementById("formEquipamento").reset();

            await listarEquipamentos();

        } catch (erro) {
            document.getElementById("mensagemEquipamento").textContent =
                erro.message;
        }
    });
async function listarColaboradores() {
    const resposta = await fetch("/colaboradores");
    const colaboradores = await resposta.json();

    const lista = document.getElementById("listaColaboradores");

    lista.innerHTML = "";

    colaboradores.forEach(colaborador => {
        const item = document.createElement("li");

        item.textContent =
            colaborador.nome + " - " +
            colaborador.email + " - " +
            (colaborador.cargo || "Cargo não informado");

        lista.appendChild(item);
    });
    return colaboradores;
}

listarColaboradores();
document.getElementById("formColaborador")
    .addEventListener("submit", async function(event) {

        event.preventDefault();

        const colaborador = {
            nome: document.getElementById("nomeColaborador").value,
            email: document.getElementById("emailColaborador").value,
            cargo: document.getElementById("cargoColaborador").value
        };

        try {
            const resposta = await fetch("/colaboradores", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(colaborador)
            });

            if (!resposta.ok) {
                throw new Error("Não foi possível cadastrar o colaborador.");
            }

            document.getElementById("mensagemColaborador").textContent =
                "Colaborador cadastrado com sucesso!";

            document.getElementById("formColaborador").reset();

            await listarColaboradores();

        } catch (erro) {
            document.getElementById("mensagemColaborador").textContent =
                erro.message;
        }
    });
async function carregarEquipamentosDisponiveis() {
    const resposta = await fetch("/equipamentos");
    const equipamentos = await resposta.json();

    const selecao = document.getElementById("equipamentoEmprestimo");

    selecao.innerHTML =
        '<option value="">Selecione um equipamento</option>';

    equipamentos
        .filter(equipamento => equipamento.disponivel)
        .forEach(equipamento => {
            const opcao = document.createElement("option");

            opcao.value = equipamento.id;
            opcao.textContent =
                equipamento.patrimonio + " - " + equipamento.nome;

            selecao.appendChild(opcao);
        });
}

carregarEquipamentosDisponiveis();
async function carregarColaboradoresEmprestimo() {
    const resposta = await fetch("/colaboradores");
    const colaboradores = await resposta.json();

    const selecao = document.getElementById("colaboradorEmprestimo");

    selecao.innerHTML =
        '<option value="">Selecione um colaborador</option>';

    colaboradores.forEach(colaborador => {
        const opcao = document.createElement("option");

        opcao.value = colaborador.id;
        opcao.textContent = colaborador.nome;

        selecao.appendChild(opcao);
    });
}

carregarColaboradoresEmprestimo();
document.getElementById("formEmprestimo")
    .addEventListener("submit", async function(event) {

        event.preventDefault();

        const equipamentoId =
            document.getElementById("equipamentoEmprestimo").value;

        const colaboradorId =
            document.getElementById("colaboradorEmprestimo").value;

        try {
            const resposta = await fetch(
                `/emprestimos?equipamentoId=${equipamentoId}&colaboradorId=${colaboradorId}`,
                {
                    method: "POST"
                }
            );

            if (!resposta.ok) {
                throw new Error("Não foi possível registrar o empréstimo.");
            }

            alert("Empréstimo registrado com sucesso!");

            await listarEquipamentos();
            await carregarEquipamentosDisponiveis();
            await listarEmprestimos();

        } catch (erro) {
            alert(erro.message);
        }
    });
async function listarEmprestimos() {
    const resposta = await fetch("/emprestimos");
    const emprestimos = await resposta.json();
    const equipamentos = await listarEquipamentos();
    const colaboradores = await listarColaboradores();

    const lista = document.getElementById("listaEmprestimos");

    lista.innerHTML = "";

    emprestimos.forEach(emprestimo => {
        const item = document.createElement("li");

        const equipamento = equipamentos.find(
            e => e.id === emprestimo.equipamentoId
        );

        const colaborador = colaboradores.find(
            c => c.id === emprestimo.colaboradorId
        );

        let situacao;

        if (emprestimo.dataDevolucao !== null) {
            situacao = "Devolvido";
        } else if (emprestimo.atrasado) {
            situacao = "Atrasado";
        } else {
            situacao = "Ativo";
        }

        item.textContent =
            "Empréstimo #" + emprestimo.id +
            " | Equipamento: " + (equipamento?.nome ?? "Não encontrado") +
            " | Colaborador: " + (colaborador?.nome ?? "Não encontrado") +
            " | Data: " + emprestimo.dataEmprestimo +
            " | Previsão: " + emprestimo.dataPrevistaDevolucao +
            " | Situação: " + situacao;

        if (emprestimo.dataDevolucao === null) {
            const botao = document.createElement("button");

            botao.textContent = "Devolver";

            botao.addEventListener("click", async function() {
                await devolverEmprestimo(emprestimo.id);
            });

            item.appendChild(botao);
        }

        lista.appendChild(item);
    });
}

listarEmprestimos();

async function devolverEmprestimo(id) {
    try {
        const resposta = await fetch(
            `/emprestimos/${id}/devolucao`,
            {
                method: "PUT"
            }
        );

        if (!resposta.ok) {
            throw new Error("Não foi possível devolver o equipamento.");
        }

        alert("Equipamento devolvido com sucesso!");

        await listarEmprestimos();
        await listarEquipamentos();
        await carregarEquipamentosDisponiveis();

    } catch (erro) {
        alert(erro.message);
    }
}