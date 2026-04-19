package com.example.koincrypto.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koincrypto.databinding.FragmentListBinding
import com.example.koincrypto.model.CryptoModel
import com.example.koincrypto.viewmodel.CryptoViewModel
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment(), RecyclerViewAdapter.Listener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var cryptoAdapter = RecyclerViewAdapter(arrayListOf(),this)
    private val viewModel: CryptoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDataFromAPI()

        observeLiveData()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

    }

    fun observeLiveData() {
        viewModel.cryptoList.observe(viewLifecycleOwner, Observer {cryptos ->

            cryptos?.let {
                binding.recyclerView.visibility = View.VISIBLE
                cryptoAdapter = RecyclerViewAdapter(ArrayList(cryptos.data ?: arrayListOf()),this@ListFragment)
                binding.recyclerView.adapter = cryptoAdapter
                binding.cryptoErrorText.visibility = View.GONE
                binding.progressBar.visibility = View.GONE

            }

        })

        viewModel.cryptoError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it.data == true) {
                    binding.cryptoErrorText.visibility = View.VISIBLE
                } else {
                    binding.cryptoErrorText.visibility = View.GONE
                }
            }
        })

        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it.data == true) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.cryptoErrorText.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE

                }
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(requireContext(),"Clicked on: ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }
}