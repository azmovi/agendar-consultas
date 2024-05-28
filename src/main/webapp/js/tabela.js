const filtro = document.querySelector("#filtro")

filtro.addEventListener("input", async function(){
    let pesquisa = filtro.value;

    const response = await fetch(`/AgendarConsultas/profissional?action=filtrar&pesquisa=${pesquisa}`);
    const listaProfissionais = await response.json();
    console.log(listaProfissionais);

})
